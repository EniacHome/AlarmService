package com.eniacdevelopment.EniacHome.Repositories.Shared;

import com.eniacdevelopment.EniacHome.DataModel.Configuration.Configuration;

/**
 * Created by larsg on 12/9/2016.
 */
public interface ConfigurationRepository<TConfiguration extends Configuration> extends Repository<TConfiguration> {
    TConfiguration getActiveConfiguration();
}
