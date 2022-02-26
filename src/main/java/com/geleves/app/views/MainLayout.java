package com.geleves.app.views;

import com.geleves.app.security.SecurityService;
import com.geleves.app.views.list.ElevesListView;
import com.geleves.app.views.list.ParentsListView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;

public class MainLayout extends AppLayout {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final SecurityService securityService;

    public MainLayout(SecurityService securityService) {
        this.securityService = securityService;
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("Geleves");
        logo.addClassNames("text-l", "m-m");

        Button logout = new Button("Se Deconnecter", e -> securityService.logout());

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo, logout);

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(logo);
        header.setWidth("100%");
        header.addClassNames("py-0", "px-m");

        addToNavbar(header);

    }

    private void createDrawer() {
        RouterLink elevesListLink = new RouterLink("Liste Des Élèves", ElevesListView.class);
        elevesListLink.setHighlightCondition(HighlightConditions.sameLocation());
        
        RouterLink parentsListLink = new RouterLink("Liste Des Parents", ParentsListView.class);
        parentsListLink.setHighlightCondition(HighlightConditions.sameLocation());

        addToDrawer(new VerticalLayout(
            elevesListLink,
            parentsListLink,
            new RouterLink("Dashboard", DashboardView.class)
        ));
    }
}
