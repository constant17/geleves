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
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
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
    	
    	viewTitle = new H1("Gélèves | "+this.contentPage);
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
        RouterLink elevesListLink = new RouterLink(null, ElevesListView.class);
        elevesListLink.setHighlightCondition(HighlightConditions.sameLocation());
        elevesListLink.setClassName("main-layout__left-nav-item");
        elevesListLink.add(new Icon(VaadinIcon.USERS));
		elevesListLink.add(new Span("   "));
        elevesListLink.add(new Span("Élèves"));
        Tab eleves = new Tab();
        eleves.add(elevesListLink);
        
        RouterLink parentsListLink = new RouterLink(null, ParentsListView.class);
        parentsListLink.setHighlightCondition(HighlightConditions.sameLocation());
        parentsListLink.setClassName("main-layout__left-nav-item");
        parentsListLink.add( new Icon(VaadinIcon.USER));
        parentsListLink.add(new Span("   "));
        parentsListLink.add(new Span("Parents"));
        Tab parents = new Tab();
        parents.add(parentsListLink);
        
        RouterLink enseignantsListLink = new RouterLink(null, EnseignantsListView.class);
        enseignantsListLink.setHighlightCondition(HighlightConditions.sameLocation());
        enseignantsListLink.setClassName("main-layout__left-nav-item");
        enseignantsListLink.add(new Icon(VaadinIcon.SPECIALIST));
        enseignantsListLink.add(new Span("   "));
        enseignantsListLink.add(new Span("Enseignants"));
        Tab enseignants = new Tab();
        enseignants.add(enseignantsListLink);
        
        RouterLink activitesLink = new RouterLink(null, ActiviteView.class);
        activitesLink.setHighlightCondition(HighlightConditions.sameLocation());
        activitesLink.setClassName("main-layout__left-nav-item");
        activitesLink.add(new Icon(VaadinIcon.CALENDAR_CLOCK));
        activitesLink.add(new Span("   "));
        activitesLink.add(new Span("Activités"));
        Tab activites = new Tab();
        activites.add(activitesLink);
        
        RouterLink dashLink = new RouterLink(null, DashboardView.class);
        dashLink.setHighlightCondition(HighlightConditions.sameLocation());
        dashLink.setClassName("main-layout__left-nav-item");
        dashLink.add(new Icon(VaadinIcon.DASHBOARD));
        dashLink.add(new Span("   "));
        dashLink.add(new Span("Tableau de board"));
        Tab dashboard = new Tab();
        dashboard.add(dashLink);
        
        com.vaadin.flow.component.html.Section section = new com.vaadin.flow.component.html.Section(new VerticalLayout(
                eleves,
                parents,
                enseignants,
                activites,
               dashboard
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
