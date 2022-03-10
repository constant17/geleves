package com.geleves.app.views;

import com.geleves.app.security.SecurityService;
import com.geleves.app.views.list.ElevesListView;
import com.geleves.app.views.list.EnseignantsListView;
import com.geleves.app.views.list.ParentsListView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLink;

public class MainLayout extends AppLayout {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final SecurityService securityService;
	private String contentPage;
	private H1 viewTitle; 

    public MainLayout(SecurityService securityService) {
        this.securityService = securityService;
        contentPage = "Élèves";
        createHeader();
        createDrawer();
    }

    private void createHeader() {
    	
    	viewTitle = new H1("Geleves | "+this.contentPage);
    	viewTitle.addClassNames("text-l", "m-m");

        Button logout = new Button("Se Deconnecter", e -> securityService.logout());

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), viewTitle, logout);

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(viewTitle);
        header.setWidth("100%");
        header.addClassNames("py-0", "px-m");

        addToNavbar(header);

    }

    private void createDrawer() {
        RouterLink elevesListLink = new RouterLink("Élèves", ElevesListView.class);
        elevesListLink.setHighlightCondition(HighlightConditions.sameLocation());
        
        RouterLink parentsListLink = new RouterLink("Parents", ParentsListView.class);
        parentsListLink.setHighlightCondition(HighlightConditions.sameLocation());
        
        RouterLink enseignantsListLink = new RouterLink("Enseignants", EnseignantsListView.class);
        enseignantsListLink.setHighlightCondition(HighlightConditions.sameLocation());
        
        RouterLink activitesLink = new RouterLink("Activites", ActiviteView.class);
        activitesLink.setHighlightCondition(HighlightConditions.sameLocation());
        
        com.vaadin.flow.component.html.Section section = new com.vaadin.flow.component.html.Section(new VerticalLayout(
                elevesListLink,
                parentsListLink,
                enseignantsListLink,
                activitesLink,
                new RouterLink("Dashboard", DashboardView.class)
            ), createFooter());
        section.addClassNames("flex", "flex-col", "items-stretch", "max-h-full", "min-h-full");
        

        addToDrawer(new VerticalLayout(
            section
        ));
    }
    
    private Footer createFooter() {
        Footer layout = new Footer();
        layout.addClassNames("flex", "items-center", "my-s", "px-m", "py-xs");

        return layout;
    }
    
    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
