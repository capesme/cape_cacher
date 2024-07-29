package com.github.voxxin.cape_cacher.config;

import com.github.voxxin.cape_cacher.client.CapeCacher;
import com.github.voxxin.web.AbstractRoute;
import com.github.voxxin.web.request.FormattedRequest;

import java.io.IOException;
import java.io.OutputStream;

public class ConfigPage extends AbstractRoute {
    public ConfigPage() {
        super(CapeCacher.MODID + "/");
    }

    @Override
    public OutputStream handleRequests(FormattedRequest request, OutputStream outputStream) throws IOException {
        return super.handleRequests(request, outputStream);
    }
}
