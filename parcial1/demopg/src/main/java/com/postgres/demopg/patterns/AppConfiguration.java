package com.postgres.demopg.patterns;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Singleton Pattern Implementation para configuración global.
 * Garantiza una única instancia de AppConfiguration en toda la aplicación.
 *
 * @author Diego Groot
 * @version 1.0
 */
public class AppConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(AppConfiguration.class);
    private static AppConfiguration instance;
    private final String appVersion;
    private final String apiVersion;
    private final int maxTweetLength;
    private final long requestTimeoutMs;

    // Constructor privado para prevenir instantiación desde fuera
    private AppConfiguration() {
        this.appVersion = "1.0.0";
        this.apiVersion = "v1";
        this.maxTweetLength = 280;
        this.requestTimeoutMs = 30000;
        logger.info("AppConfiguration inicializado: {} - API {}", appVersion, apiVersion);
    }

    /**
     * Obtiene la instancia única de AppConfiguration (lazy initialization).
     *
     * @return instancia de AppConfiguration
     */
    public static synchronized AppConfiguration getInstance() {
        if (instance == null) {
            instance = new AppConfiguration();
        }
        return instance;
    }

    /**
     * Obtiene la versión de la aplicación.
     *
     * @return versión de la aplicación
     */
    public String getAppVersion() {
        return appVersion;
    }

    /**
     * Obtiene la versión de la API.
     *
     * @return versión de la API
     */
    public String getApiVersion() {
        return apiVersion;
    }

    /**
     * Obtiene la longitud máxima de tweets.
     *
     * @return longitud máxima en caracteres
     */
    public int getMaxTweetLength() {
        return maxTweetLength;
    }

    /**
     * Obtiene el timeout para requests.
     *
     * @return timeout en milisegundos
     */
    public long getRequestTimeoutMs() {
        return requestTimeoutMs;
    }

    /**
     * Obtiene información de configuración.
     *
     * @return string con información de configuración
     */
    public String getInfo() {
        return String.format("App v%s - API %s - MaxTweet: %d - Timeout: %dms",
                appVersion, apiVersion, maxTweetLength, requestTimeoutMs);
    }
}
