package com.mapfre.tron.config.api;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mapfre.dgtp.gaia.commons.GaiaConstants.ZeroConfig;
import com.mapfre.dgtp.gaia.commons.beans.factory.support.CustomBeanNameGenerator;
import com.mapfre.dgtp.gaia.commons.config.GaiaBlConfiguration;
import com.mapfre.dgtp.gaia.connector.metadata.rules.MnemonicRule.Type;
import com.mapfre.dgtp.gaia.connector.metadata.rules.MnemonicRuleConfiguratorBean;
import com.mapfre.dgtp.gaia.mapper.GaiaMapperConfiguration;
import com.mapfre.dgtp.plinvoker.config.PlInvokerConfiguration;
import com.mapfre.nwt.connector.metadata.rules.NewtronBoObjectRule;
import com.mapfre.nwt.connector.metadata.rules.NewtronDatabaseFieldRule;
import com.mapfre.nwt.connector.metadata.rules.NewtronListRule;
import com.mapfre.nwt.connector.metadata.rules.NewtronMethodNameRule;
import com.mapfre.nwt.connector.metadata.rules.NewtronPackageNameRule;

@Configuration
@GaiaBlConfiguration
@EnableCaching
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableTransactionManagement
@ComponentScan(
        basePackages = {"com.mapfre.tron.api", "com.mapfre.nwt.trn", "com.mapfre.nwt.ins.c", "com.mapfre.nwt.cache",
                "com.mapfre.tron.api.cache", "com.mapfre.tron.gdc", "com.mapfre.tron.sfv" },
        nameGenerator = CustomBeanNameGenerator.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Controller.class),
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "\\.ws\\.SSr\\..*"),
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com\\.mapfre\\.nwt\\..*\\.sr\\.proxy\\..*")
        })
@Import({
        GaiaMapperConfiguration.class,
        DirectDatasourceConfig.class,
        RealDatasourceConfig.class,
        PlInvokerConfiguration.class})
public class NwtApiBlConfig {
    
    @Bean
    MnemonicRuleConfiguratorBean customPlInvokerRules(Environment env) {
      String objectPrefix = env.getProperty(ZeroConfig.PlInvoker.ObjectPrefix);
      MnemonicRuleConfiguratorBean bean = new MnemonicRuleConfiguratorBean(objectPrefix);

      bean.getRulesMap().put(Type.PACKAGE, new NewtronPackageNameRule());
      bean.getRulesMap().put(Type.METHOD, new NewtronMethodNameRule());
      NewtronListRule listRule=new NewtronListRule();
      listRule.setObjectPreffix(objectPrefix);
      bean.getRulesMap().put(Type.LIST, listRule);
      bean.getRulesMap().put(Type.ENTITY_OBJECT, new NewtronBoObjectRule());
      bean.getRulesMap().put(Type.DATABASE_FIELD, new NewtronDatabaseFieldRule());

      return bean;
    }

    @Bean
    @Primary
    public CacheManager cacheManager() {
        return new EhCacheCacheManager(ehCacheCacheManager().getObject());
    }

    @Bean
    public EhCacheManagerFactoryBean ehCacheCacheManager() {
        EhCacheManagerFactoryBean cmfb = new EhCacheManagerFactoryBean();
        cmfb.setConfigLocation(new ClassPathResource("ehcache-config.xml"));
        cmfb.setShared(true);
        return cmfb;
    }

}
