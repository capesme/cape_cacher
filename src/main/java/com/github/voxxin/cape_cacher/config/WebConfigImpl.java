package com.github.voxxin.cape_cacher.config;

import com.github.voxxin.web.AbstractRoute;
import com.github.voxxin.web.WebServer;
import com.github.voxxin.webconfig.api.WebConfigAPI;
import com.github.voxxin.webconfig.web.api.PublicPath;

import java.util.List;

public class WebConfigImpl implements WebConfigAPI {

    @Override
    public AbstractRoute defaultConfigRouteFactory() {
        return new ConfigPage();
    }

    @Override
    public List<PublicPath> configPublicFiles() {
        return List.of(new PublicPath("assets/cape_cacher/web/", "assets/cape_cacher/", WebServer.PathType.INTERNAL, WebServer.DirectoryPosition.CURRENT));
    }
}
