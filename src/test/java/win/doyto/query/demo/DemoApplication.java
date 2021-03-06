package win.doyto.query.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import win.doyto.query.service.AssociativeService;
import win.doyto.query.service.AssociativeServiceTemplate;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * DemoApplication
 *
 * @author f0rb
 */
@SpringBootApplication
@EnableCaching(proxyTargetClass = true)
@EnableTransactionManagement(proxyTargetClass = true)
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class);
    }

    @Resource
    public void setDataSource(DataSource dataSource) {
        new ResourceDatabasePopulator(new ClassPathResource("import.sql")).execute(dataSource);
    }

    @Bean
    public AssociativeService<Long, Integer> userAndRoleAssociativeService() {
        return new AssociativeServiceTemplate<>("t_user_and_role", "userId", "roleId", "createUserId");
    }

    @Bean
    public AssociativeService<Integer, Integer> roleAndPermissionAssociativeService() {
        return new AssociativeServiceTemplate<>("t_role_and_permission", "roleId", "permissionId");
    }
}
