package org.clover4j.framework;

/**
 * @author smallclover
 * @create 2017-01-03
 * @since 1.0.0
 */
public interface ConfigConstant {

    String CONFIG_FILE = "clover.properties";

    String JDBC_DRIVER = "clover.framework.jdbc.driver";
    String JDBC_URL = "clover.framework.jdbc.url";
    String JDBC_USERNAME = "clover.framework.jdbc.username";
    String JDBC_PASSWORD = "clover.framework.jdbc.password";

    String APP_BASE_PACKAGE = "clover.framework.app.base_package";
    String APP_JSP_PATH = "clover.framework.app.jsp_path";
    String APP_ASSET_PATH = "clover.framework.app.asset_path";
    String APP_UPLOAD_LIMIT = "clover.frameworl.app.upload_limit";
}
