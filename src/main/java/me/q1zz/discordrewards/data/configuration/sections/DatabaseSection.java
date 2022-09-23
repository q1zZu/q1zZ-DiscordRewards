package me.q1zz.discordrewards.data.configuration.sections;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.CustomKey;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class DatabaseSection extends OkaeriConfig {

    private String host = "localhost";

    private int port = 3306;

    private String database = "db_name";

    private String username = "root";

    private String password = "password";

    @CustomKey("use-ssl")
    private boolean useSSL = false;

}
