package com.leyou.item.web;

import com.leyou.common.vo.PageResult;
import com.leyou.item.pojo.Brand;
import com.leyou.item.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @outhor Mr.JK
 * @create 2020-05-14  13:33
 */
@RestController
@RequestMapping("brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    /**
     * 分页查询品牌
     * @param page 页码开始
     * @param rows  一页条数
     * @param sortBy  排序字段
     * @param desc 排序方式
     * @param key   搜索栏中数据
     * @return
     */
    @GetMapping("page")//查用get
    public ResponseEntity<PageResult<Brand>> queryBrandByPage(
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "rows",defaultValue = "5") Integer rows,
            @RequestParam(value = "sortBy",required = false) String sortBy,
            @RequestParam(value = "desc",defaultValue = "false") Boolean desc,
            @RequestParam(value = "key",required = false) String key
    ){
        /*查询成功状态码200 ok已封装*/
        return ResponseEntity.ok(brandService.queryBrandByPage(page,rows,sortBy,desc,key));
    }

    /**
     * 保存新品牌，并维护中间表
     * @param brand
     * @param cids
     * @return
     */
    @PostMapping    //新增保存用post
    public ResponseEntity<Void> saveBrand(Brand brand ,@RequestParam("cids") List<Long> cids){
        brandService.saveBrand(brand,cids);
        return ResponseEntity.status(HttpStatus.CREATED).build();//build没有东西返回。新建成功状态码201
    }

    /**
     * 修改品牌信息
     * @param brand
     * @return
     */
    @PutMapping     //改
    public ResponseEntity<Void> updateBrand(Brand brand){
        brandService.updateBrand(brand);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    /**
     * 删除品牌信息，暂时有bug 会导致商品查询抛出异常 因为某品牌查出为null
     * @param id
     * @return
     */
    @DeleteMapping("id/{id}")
    public ResponseEntity<Void> deleteBrand(@PathVariable("id") Long id){
        brandService.deleteBrand(id);
        return ResponseEntity.status(HttpStatus.OK).build();

    }

    /**
     * 根据cid查询品牌
     * @param cid
     * @return
     */
    @GetMapping("/cid/{cid}")
    public ResponseEntity<List<Brand>> queryBrandByCid(@PathVariable("cid") Long cid){
        return ResponseEntity.ok(brandService.queryBrandByCid(cid));
    }

    /**
     * 根据id查询品牌
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public ResponseEntity<Brand> queryBrandById(@PathVariable("id") Long id){
        return ResponseEntity.ok(brandService.queryById(id));
    }

    @GetMapping("list")
    public ResponseEntity<List<Brand>> queryBrandByIds(@RequestParam("ids") List<Long> ids){
        return ResponseEntity.ok(brandService.querybyIds(ids));
    }



}
