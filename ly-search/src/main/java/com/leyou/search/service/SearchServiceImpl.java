package com.leyou.search.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.utils.JsonUtils;
import com.leyou.common.vo.PageResult;
import com.leyou.item.pojo.*;
import com.leyou.search.client.BrandClient;
import com.leyou.search.client.CategoryClient;
import com.leyou.search.client.GoodsClient;
import com.leyou.search.client.SpecificationClient;
import com.leyou.search.pojo.Goods;
import com.leyou.search.pojo.SearchRequest;
import com.leyou.search.pojo.SearchResult;
import com.leyou.search.repository.GoodsRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.*;

import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.InternalTerms;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;


import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Mr.JK
 * @create 2020-05-19  20:06
 */
@Slf4j
@Service
public class SearchServiceImpl implements SearchService{

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private BrandClient brandClient;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private SpecificationClient specClient;

    @Autowired
    private GoodsRepository repository;

    @Autowired
    private ElasticsearchTemplate template;

    public Goods buildGoods(Spu spu){
        Long spuId = spu.getId();

        //查询分类
        List<Category> categories = categoryClient.queryGategoryByIds(
                Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
        if (CollectionUtils.isEmpty(categories)){
            throw new LyException(ExceptionEnum.CATEGORY_NOT_FOND);
        }
        List<String> names = categories.stream().map(Category::getName).collect(Collectors.toList());

        //查询品牌
        Brand brand = brandClient.queryBrandById(spu.getBrandId());
        if (brand == null){
            throw new LyException(ExceptionEnum.BRAND_NOT_FOUND);
        }

        //搜索字段
        String all = spu.getTitle() + StringUtils.join(names," ") + brand.getName();

        //查询sku
        List<Sku> skuList = goodsClient.querySkuBySpuId(spuId);
        if (CollectionUtils.isEmpty(skuList)){
            throw new LyException(ExceptionEnum.GOODS_SKU_NOT_FOND);
        }
        //对sku进行处理
        List<Map<String,Object>> skus = new ArrayList<>();
        //价格集合
        Set<Long> priceSet = new HashSet<>();
        for (Sku sku : skuList) {
            Map<String,Object> map = new HashMap<>();
            map.put("id",sku.getId());
            map.put("title",sku.getTitle());
            map.put("price",sku.getPrice());
            map.put("images",StringUtils.substringBefore(sku.getImages(),","));
            skus.add(map);
            //处理价格
            priceSet.add(sku.getPrice());
        }

        //查询规格参数
        List<SpecParam> params = specClient.queryParamByGid(null, spu.getCid3(), true);
        if (CollectionUtils.isEmpty(params)){
            throw new LyException(ExceptionEnum.SPEC_PARAM_NOT_FOND);
        }
        //查询商品详情
        SpuDetail spuDetail = goodsClient.queryDetailById(spuId);
        //获取通用规格参数
        Map<Long, Object> genericSpec = JsonUtils.nativeRead(spuDetail.getGenericSpec(),
                new TypeReference<Map<Long, Object>>() {});
        //获取特有规格参数
        Map<Long, List<String>> specialSpec = JsonUtils.nativeRead(spuDetail.getSpecialSpec(),
                new TypeReference<Map<Long, List<String>>>() {});

        // 定义map接收{规格参数名，规格参数值}
        Map<String, Object> specs = new HashMap<>();

        for (SpecParam param : params) {
            //规格名称
            String key = param.getName();
            Object value = "";
            //判断是否通用规格
            if (param.getGeneric()){
                value = genericSpec.get(param.getId());
                //判断是否是数据类型
                if(param.getNumeric()){
                    //处理成段
                    value = chooseSegment(value.toString(),param);
                }
            }else {
                value = specialSpec.get(param.getId());
            }
            //存入map
            specs.put(key,value);
        }

        //构建goods对象
        Goods goods = new Goods();
        goods.setBrandId(spu.getBrandId());
        goods.setCid1(spu.getCid1());
        goods.setCid2(spu.getCid2());
        goods.setCid3(spu.getCid3());
        goods.setCreateTime(spu.getCreateTime());
        goods.setId(spuId);
        goods.setAll(all);// 搜索字段，包含标题，分类，品牌，规格等
        goods.setPrice(priceSet);// 所有sku的价格集合
        goods.setSkus(JsonUtils.toString(skus));// 所有sku的集合的json格式
        goods.setSpecs(specs);// 所有的可搜索的规格参数
        goods.setSubTitle(spu.getSubTitle());

        return goods;
    }


    private String chooseSegment(String value, SpecParam p) {
        double val = NumberUtils.toDouble(value);
        String result = "其它";
        // 保存数值段
        for (String segment : p.getSegments().split(",")) {
            String[] segs = segment.split("-");
            // 获取数值范围
            double begin = NumberUtils.toDouble(segs[0]);
            double end = Double.MAX_VALUE;
            if(segs.length == 2){
                end = NumberUtils.toDouble(segs[1]);
            }
            // 判断是否在范围内
            if(val >= begin && val < end){
                if(segs.length == 1){
                    result = segs[0] + p.getUnit() + "以上";
                }else if(begin == 0){
                    result = segs[1] + p.getUnit() + "以下";
                }else{
                    result = segment + p.getUnit();
                }
                break;
            }
        }
        return result;
    }


    public PageResult<Goods> search(SearchRequest request) {
        String key = request.getKey();
        //判断是否有搜索条件，如果没有，直接返回null，不允许搜索全部商品
        if(StringUtils.isBlank(key)){
            return null;
        }

        Integer page = request.getPage() - 1;//细节 elasticsearch分页默认从0开始
        Integer size = request.getSize();
        //创建查询构建器
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //结构过滤
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{"id","subTitle","skus"},null));
        //分页
        queryBuilder.withPageable(PageRequest.of(page,size));

        //排序 按销量与按评价查询未做
        String sortBy = request.getSortBy();
        Boolean desc = request.getDescending();
        if (StringUtils.isNotBlank(sortBy)){
            //如果不为空则进行排序
            queryBuilder.withSort(SortBuilders.fieldSort(sortBy).order(desc ? SortOrder.DESC : SortOrder.ASC));
        }

        //过滤 ,搜索条件
        QueryBuilder basicQuery = buildBasicQuery(request);
        queryBuilder.withQuery(basicQuery);

        //聚合分类和品牌
        //聚合分类
        String categoryAggName = "category_agg";
        queryBuilder.addAggregation(AggregationBuilders.terms(categoryAggName).field("cid3"));

        //聚合品牌
        String brandAggName = "brand_agg";
        queryBuilder.addAggregation(AggregationBuilders.terms(brandAggName).field("brandId"));


        //查询
        AggregatedPage<Goods> result = template.queryForPage(queryBuilder.build(),Goods.class);

        //解析结构
        //1.解析分页结果
        long total = result.getTotalElements();
        int totalpage = result.getTotalPages();
        List<Goods> goodsList = result.getContent();

        //2.解析聚合结果
        Aggregations aggs = result.getAggregations();
        List<Category> categories = parseCategoryAgg(aggs.get(categoryAggName));
        List<Brand> brands = parseBrandAgg(aggs.get(brandAggName));

        //完成规格参数聚合
        List<Map<String,Object>> specs = null;
        if (categories != null && categories.size() == 1){
            //商品分类存在并且数量为1，可以聚合规格参数
            specs = buildSpecififcationAgg(categories.get(0).getId(),basicQuery);
        }

        return new SearchResult(total,totalpage,goodsList,categories,brands,specs);
    }

    private QueryBuilder buildBasicQuery(SearchRequest request) {
        //创建布尔查询
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        //查询条件
        queryBuilder.must(QueryBuilders.matchQuery("all",request.getKey()));
        //过滤条件
        Map<String, String> map = request.getFilter();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            //处理key
            if (!"cid3".equals(key) && !"brandId".equals(key)){
                key = "specs." + key + ".keyword";
            }
            queryBuilder.filter(QueryBuilders.termQuery(key,entry.getValue()));
        }

        return queryBuilder;

    }



    private List<Map<String, Object>> buildSpecififcationAgg(Long cid, QueryBuilder basicQuery) {
        ArrayList<Map<String,Object>> specs = new ArrayList<>();
        //1.查询需要聚合的规格参数
        List<SpecParam> params = specClient.queryParamByGid(null, cid, true);
        //2.聚合
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //2.1带上查询条件
        queryBuilder.withQuery(basicQuery);
        //2.2聚合
        for (SpecParam param : params) {
            String name = param.getName();
            queryBuilder.addAggregation(
                    AggregationBuilders.terms(name).field("specs." + name + ".keyword"));
        }
        //3.获取结果
        AggregatedPage<Goods> result = template.queryForPage(queryBuilder.build(), Goods.class);

        //4.解析结构
        Aggregations aggs = result.getAggregations();
        for (SpecParam param : params) {
            String name = param.getName();
            StringTerms terms = aggs.get(name);
            //准备map
            HashMap<String, Object> map = new HashMap<>();
            map.put("k",name);
            map.put("options",terms.getBuckets()
                    .stream().map(b -> b.getKeyAsString()).collect(Collectors.toList()));
            specs.add(map);
        }

        return specs;
    }


    private List<Brand> parseBrandAgg(InternalTerms aggregation) {

        try {
            LongTerms terms = (LongTerms) aggregation;
            List<Long> ids = terms.getBuckets()
                    .stream().map(b -> b.getKeyAsNumber().longValue())
                    .collect(Collectors.toList());
            List<Brand> brands = brandClient.queryBrandByIds(ids);
            return brands;
        } catch (Exception e) {
            log.error("[搜索服务]查询匹配异常：",e);
            return null;
        }
    }

    private List<Category> parseCategoryAgg(InternalTerms aggregation) {

        try {
            LongTerms terms = (LongTerms) aggregation;
            List<Long> ids = terms.getBuckets()
                    .stream().map(b -> b.getKeyAsNumber().longValue())
                    .collect(Collectors.toList());
            List<Category> categories = categoryClient.queryGategoryByIds(ids);
            return categories;
        } catch (Exception e) {
            log.error("[搜索服务]查询分类异常：",e);
            return null;
        }
    }


    public void createOrUpdateIndex(Long spuId) {
        //查询spu
        Spu spu = goodsClient.querySpuById(spuId);

        //构建goods
        Goods goods = buildGoods(spu);
        //存入索引库
        repository.save(goods);
    }

    public void deleteIndex(Long spuId) {
        repository.deleteById(spuId);
    }
}
