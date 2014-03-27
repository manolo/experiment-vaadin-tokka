package org.apache.hupa.vaadin.mobile;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import com.vaadin.addon.touchkit.server.TouchKitServlet;
import com.vaadin.addon.touchkit.settings.ApplicationCacheSettings;
import com.vaadin.server.BootstrapPageResponse;
import com.vaadin.server.ServiceException;
import com.vaadin.server.SessionInitEvent;
import com.vaadin.server.SessionInitListener;

@SuppressWarnings("serial")
@WebServlet("/*")
public class AppServlet extends TouchKitServlet {

    private AppUIProvider uiProvider = new AppUIProvider();

    @Override
    protected void servletInitialized() throws ServletException {
        super.servletInitialized();
//        getTouchKitSettings().setApplicationCacheSettings(
//                new ApplicationCacheSettings() {
//                    @Override
//                    public void modifyBootstrapPage(BootstrapPageResponse response) {
//                        super.modifyBootstrapPage(response);
//                        response.getDocument()
//                                .getElementsByTag("html")
//                                .attr("manifest",
//                                        "./VAADIN/widgetsets/org.apache.hupa.vaadin.gwt.AppWidgetSet/"
//                                                + generateManifestFileName(response));
//                    }
//                });
        getTouchKitSettings().getApplicationCacheSettings()
                .setCacheManifestEnabled(true);
        getService().addSessionInitListener(new SessionInitListener() {
            @Override
            public void sessionInit(SessionInitEvent event)
                    throws ServiceException {
                event.getSession().addUIProvider(uiProvider);
            }
        });
    }

}
