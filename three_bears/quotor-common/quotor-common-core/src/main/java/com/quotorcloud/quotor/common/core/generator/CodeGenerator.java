package com.quotorcloud.quotor.common.core.generator;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName CodeGenerator
 * @Author tianshihao
 * @Date 2019/2/15 9:40
 * @Version 1.0
 **/
public class CodeGenerator {

    private static final String[] TABLES_NAME = {"bear_member_integral"};

    private static final String PREIX = "mi";

    public static void main(String[] args) {
        String[] models = {"quotor-academy-api","quotor-academy"};
//        String[] models = {"quotor-upms-api","quotor-upms"};
        for (String model : models) {
            generator(model);
        }
    }

    private static void generator(String model){
        if(model == null || "".equals(model)){
            return;
        }
        String[] split = model.split("-");
        String end = split.length>0?split[split.length-1]:null;
        //代码生成器
        AutoGenerator mpg = new AutoGenerator();

        //全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir")+ File.separator + "quotor-academy" + File.separator + ("api".equals(end)?model:model+"-biz");
        gc.setOutputDir(projectPath + "\\src\\main\\java");
        gc.setAuthor("tianshihao");
        gc.setFileOverride(true);
        gc.setIdType(IdType.UUID);
        gc.setServiceName("%sService");
        gc.setEntityName("%s");
        gc.setMapperName("%sMapper");
        gc.setServiceImplName("%sServiceImpl");
        gc.setControllerName("%sController");
        gc.setOpen(false);
        gc.setBaseResultMap(true);
        gc.setEnableCache(false);
        gc.setFileOverride(true);
        gc.setBaseColumnList(true);
        mpg.setGlobalConfig(gc);

        //数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUrl("jdbc:mysql://quotor-mysql:3306/three_bears?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&allowMultiQueries=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai");
        dsc.setUsername("root");
        dsc.setPassword("123456");
        mpg.setDataSource(dsc);

        //包配置
        PackageConfig pc = new PackageConfig();
//        pc.setModuleName(scanner("模块名"));
//        pc.setModuleName(model);
        pc.setParent("com.quotorcloud."+model.replace("-","."))
                .setMapper("mapper")
                .setEntity("api.entity")
                .setController("controller")
                .setService("service");
        mpg.setPackageInfo(pc);
        if(!"api".equals(end)) {
            //自定义配置
            InjectionConfig cfg = new InjectionConfig() {
                @Override
                public void initMap() {

                }
            };

            // 如果模板引擎是 freemarker
            String templatePath = "/templates/mapper.xml.ftl";
            // 如果模板引擎是 velocity
            // String templatePath = "/templates/mapper.xml.vm";

            //自定义输出配置
            List<FileOutConfig> focList = new ArrayList<>();
            //自定义配置会被优先输出
            focList.add(new FileOutConfig(templatePath) {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    return projectPath + "\\src\\main\\resources\\mapper\\"
                            + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
                }
            });

            cfg.setFileOutConfigList(focList);
            mpg.setCfg(cfg);
        }

        //配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        if("api".equals(end)){
            templateConfig.setController(null);
            templateConfig.setMapper(null);
            templateConfig.setService(null);
            templateConfig.setServiceImpl(null);
        }else {
            templateConfig.setEntity(null);
        }
        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setTablePrefix("bear_");
        strategy.setFieldPrefix(PREIX);
//        strategy.setSuperEntityClass("com.serve.ops.data.BaseEntity");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
//        strategy.setSuperControllerClass("com.baomidou.ant.common.BaseController");
        strategy.setInclude(TABLES_NAME);
//        strategy.setSuperEntityColumns("id");
        strategy.setControllerMappingHyphenStyle(true);
//        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }
}
