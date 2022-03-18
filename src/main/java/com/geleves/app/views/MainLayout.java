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
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.HighlightAction;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLink;

public class MainLayout extends AppLayout {
    /**
	 * 
	 */
	public static final int ELEVES_TAB = 0;
	public static final int PARENTS_TAB = 1;
	public static final int ENS_TAB = 2;
	public static final int ACTIVITES_TAB = 3;
	public static final int NOTES_TAB = 4;
	public static final int BULLETIN_TAB = 5;
	public static final int PAIEMENT_TAB = 6;
	public static final int DASH_TAB = 7;
	
	private static final long serialVersionUID = 1L;
	private final SecurityService securityService;
	private String contentPage;
	private H1 viewTitle; 
	
	public static Tabs tabs;

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
        elevesListLink.add(new Span("   Élèves"));
        Tab eleves = new Tab(
        		VaadinIcon.USERS.create(),
        		elevesListLink
        );
        
        RouterLink parentsListLink = new RouterLink(null, ParentsListView.class);
        parentsListLink.setHighlightCondition(HighlightConditions.sameLocation());
        parentsListLink.setClassName("main-layout__left-nav-item");
        parentsListLink.add(new Span("   Parents"));
        Tab parents = new Tab(
        		VaadinIcon.USER.create(),
        		parentsListLink
        );
        
        RouterLink enseignantsListLink = new RouterLink(null, EnseignantsListView.class);
        enseignantsListLink.setHighlightCondition(HighlightConditions.sameLocation());
        enseignantsListLink.setClassName("main-layout__left-nav-item");
        enseignantsListLink.add(new Span("   Enseignants"));
        Tab enseignants = new Tab(
        		VaadinIcon.SPECIALIST.create(),
        		enseignantsListLink
        );
        
        RouterLink activitesLink = new RouterLink(null, ActiviteView.class);
        activitesLink.setHighlightCondition(HighlightConditions.sameLocation());
        activitesLink.setClassName("main-layout__left-nav-item");
        activitesLink.add(new Span("   Activités"));
        Tab activites = new Tab(
        		VaadinIcon.CALENDAR_CLOCK.create(),
        		activitesLink
        );
        
        RouterLink notesLink = new RouterLink(null, NotesView.class);
        notesLink.setHighlightCondition(HighlightConditions.sameLocation());
        notesLink.setClassName("main-layout__left-nav-item");
        notesLink.add(new Span("   Notes"));
        Tab notes = new Tab(
        		VaadinIcon.CLIPBOARD_CHECK.create(),
        		notesLink
        	);
        
        RouterLink bulletinLink = new RouterLink(null, ElevesBulletinView.class);
        bulletinLink.setHighlightCondition(HighlightConditions.sameLocation());
        bulletinLink.setClassName("main-layout__left-nav-item");
        bulletinLink.add(new Span("   Bulletins"));
        Tab bulletin =  new Tab(
        		VaadinIcon.ARCHIVE.create(),
        		bulletinLink
        	);
        
        RouterLink paiementLink = new RouterLink(null, PaiementView.class);
        paiementLink.setHighlightCondition(HighlightConditions.sameLocation());
        paiementLink.setClassName("main-layout__left-nav-item");
        paiementLink.add(new Span("   Paiements"));
        Tab paiements =  new Tab(
        		VaadinIcon.MONEY_DEPOSIT.create(),
        		paiementLink
        	);
        
        RouterLink dashLink = new RouterLink(null, DashboardView.class);
        dashLink.setHighlightCondition(HighlightConditions.sameLocation());
        dashLink.setClassName("main-layout__left-nav-item");
        //dashLink.add(new Icon(VaadinIcon.DASHBOARD));
        //dashLink.add(new Span("   "));
        dashLink.add(new Span("   Tableau de board"));
        
        Tab dashboard = new Tab(
        		VaadinIcon.DASHBOARD.create(),
        		dashLink
        	);
        
        /*com.vaadin.flow.component.html.Section section = new com.vaadin.flow.component.html.Section(new VerticalLayout(
                eleves,
                parents,
                enseignants,
                activites,
                notes,
                bulletin,
                paiements,
               dashboard
            ), createFooter());
        section.addClassNames("flex", "flex-col", "items-stretch", "max-h-full", "min-h-full");*/
        
        tabs = new Tabs(
        		eleves,
                parents,
                enseignants,
                activites,
                notes,
                bulletin,
                paiements,
               dashboard
        	);
        	tabs.setOrientation(Tabs.Orientation.VERTICAL);
        

        addToDrawer(new VerticalLayout(
            tabs
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
