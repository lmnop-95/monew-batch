package com.monew.monew_batch.config;

import javax.sql.DataSource;

import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig extends DefaultBatchConfiguration {

    private final DataSource metaDataSource;
    private final PlatformTransactionManager metaTransactionManager;

    public BatchConfig(
        @Qualifier("metaDBSource") DataSource metaDataSource,
        @Qualifier("metaTransactionManager") PlatformTransactionManager metaTransactionManager
    ) {
        this.metaDataSource = metaDataSource;
        this.metaTransactionManager = metaTransactionManager;
    }

    @Override
    protected DataSource getDataSource() {
        return metaDataSource;
    }

    @Override
    protected PlatformTransactionManager getTransactionManager() {
        return metaTransactionManager;
    }
}
