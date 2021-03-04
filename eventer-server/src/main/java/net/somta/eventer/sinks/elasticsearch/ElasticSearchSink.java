package net.somta.eventer.sinks.elasticsearch;

import java.io.IOException;
import java.util.Date;
import net.somta.common.utils.DateUtil;
import net.somta.eventer.EventBody;
import net.somta.eventer.config.ElasticSearchConfig;
import net.somta.eventer.sinks.ISink;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description //TODO
 * @Author husong
 * @Date 2021/3/3
 * @Version 1.0
 **/
public class ElasticSearchSink implements ISink {

    private Logger logger = LoggerFactory.getLogger(ElasticSearchSink.class);

    private final ElasticSearchConfig elasticSearchConfig;

    private final RestHighLevelClient restHighLevelClient;

    public ElasticSearchSink(ElasticSearchConfig elasticSearchConfig) {
        this.elasticSearchConfig = elasticSearchConfig;
        this.restHighLevelClient = initElasticSearchClient();
    }

    @Override
    public void send(EventBody eventBody) {

    }


    /**
     * 检查索引，不存在就创建索引
     * @return
     */
    private String checkAndCreateIndex(){
        String indexName = elasticSearchConfig.getIndex() + "-" + DateUtil.dateToStr(new Date(),"");
        if(!existsIndex(indexName)){
            CreateIndexRequest request = new CreateIndexRequest(indexName);
            //创建索引时创建文档类型映射
            //request.mapping("", XContentType.JSON);
            CreateIndexResponse response = null;
            try {
                response = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
            } catch (IOException e) {
                logger.info("创建索引异常");
                e.printStackTrace();
            }
            System.out.println(response.toString());
            logger.info("索引创建结查：" + response.isAcknowledged());
        }
        return indexName;
    }

    /**
     * 判断索引是否存在
     * @param indexName
     * @return
     * @throws IOException
     */
    private boolean existsIndex(String indexName) {
        GetIndexRequest request = new GetIndexRequest(indexName);
        logger.info("source:" + request.toString());
        boolean exists = false;
        try {
            exists = restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            logger.debug("检查索引是否存在异常");
            e.printStackTrace();
        }
        logger.debug("existsIndex: " + exists);
        return exists;
    }

    /**
     * 初始化ES客户端
     * @return
     */
    private RestHighLevelClient initElasticSearchClient(){
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        if(StringUtils.isNotEmpty(elasticSearchConfig.getUsername()) && StringUtils.isNotEmpty(elasticSearchConfig.getPassword())){
            credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(elasticSearchConfig.getUsername(), elasticSearchConfig.getPassword()));
        }
        RestClientBuilder builder = RestClient.builder(new HttpHost(elasticSearchConfig.getServerUrl(), elasticSearchConfig.getServerPort(), "http"));
        builder.setRequestConfigCallback(requestConfigBuilder -> {
            requestConfigBuilder.setConnectTimeout(5000);
            requestConfigBuilder.setSocketTimeout(10000);
            requestConfigBuilder.setConnectionRequestTimeout(6000);
            return requestConfigBuilder;
        });
        builder.setHttpClientConfigCallback(httpClientBuilder -> {
            httpClientBuilder.setMaxConnTotal(100);
            httpClientBuilder.setMaxConnPerRoute(100);
            if(StringUtils.isNotEmpty(elasticSearchConfig.getUsername()) && StringUtils.isNotEmpty(elasticSearchConfig.getPassword())){
                httpClientBuilder.disableAuthCaching();
                httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
            }
            return httpClientBuilder;
        });
        return new RestHighLevelClient(builder);
    }
}
