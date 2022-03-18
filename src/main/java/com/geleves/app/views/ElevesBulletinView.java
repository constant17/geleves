package com.geleves.app.views;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.annotation.security.PermitAll;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.geleves.app.Application;
import com.geleves.app.data.entity.Classe;
import com.geleves.app.data.entity.Eleve;
import com.geleves.app.data.entity.Niveau;
import com.geleves.app.data.entity.Periode;
import com.geleves.app.data.service.GelevesService;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Component
@Scope("prototype")
@Route(value="bulletins", layout = MainLayout.class)
@PageTitle("Bulletins | Gélèves")
@PermitAll
public class ElevesBulletinView extends VerticalLayout{
	
	private static final long serialVersionUID = 1L;
	Grid<Eleve> grid = new Grid<>(Eleve.class);
	Tabs niveaux = new Tabs();
	List<Niveau> levels;
	List<Periode> periodes;
	HashMap<String, Niveau> niveauMapper = new HashMap<String, Niveau>();
	
	ComboBox<Classe> classes = new ComboBox<>("Liste des Elèves par Classe");
	ComboBox<String> anneeScolaire = new ComboBox<>("Annee Scolaire");
	ComboBox<String> trimestre = new ComboBox<>("Trimestre");
	HorizontalLayout selections = new HorizontalLayout();
	BulletinView bulletin;
	Logger logger;
	
    GelevesService service;

    public ElevesBulletinView(GelevesService service) {
    	MainLayout.tabs.setSelectedIndex(MainLayout.BULLETIN_TAB);
    	logger = LoggerFactory.getLogger(getClass());
        this.service = service;
        levels = service.findAllNiveaux();
        periodes = service.findAllPeriodes();
        
        bulletin = new BulletinView(service.getEtablissement(), null);
        bulletin.setWidth("40em");
        bulletin.addListener(BulletinView.SaveEvent.class, this::exportBulletinPDF);
        bulletin.addListener(BulletinView.PrintEvent.class, this::printBulletin);
        bulletin.addListener(BulletinView.CloseEvent.class, e -> closeBulletin());
        
        addClassName("list-view"); 
        setSizeFull();
        configureGrid();
        FlexLayout content = new FlexLayout(grid, bulletin);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, bulletin);
        content.setFlexShrink(0, bulletin);
        content.addClassNames("content", "gap-m");
        content.setSizeFull();
        closeBulletin();
        add(getToolbar(), selections, content);

        grid.asSingleSelect().addValueChangeListener(event ->
            displayBulletin(event.getValue()));
    }

    private void configureGrid() {
        grid.addClassNames("contact-grid");
        //grid.setSizeFull();
        grid.setColumns("nom", "prenom", "matricule" );
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }
    

    private void setTabContent(Tab selectedTab) {
    	
    	int niveauId = niveauMapper.get(selectedTab.getLabel()).getId();
    	
    	List<String> anneeScolairesTmp = new LinkedList<String>(); anneeScolairesTmp.add("2021-2022");
    	anneeScolaire.setItems(anneeScolairesTmp);
    	anneeScolaire.setPlaceholder("Selectionner l'annee scolaire");
    	anneeScolaire.setWidth("17em");
    	
    	Set<String> trimestres = new HashSet<String>();
    	for(Periode p:periodes) {trimestres.add((p.getTrimestre()+"e Trimestre"));}
    	trimestre.setItems(trimestres);
    	trimestre.setPlaceholder("Selectionner le trimestre");
    	trimestre.setWidth("17em");
    	
    	classes.setItems(service.findClassesByNiveau(niveauId));
    	classes.setItemLabelGenerator(Classe::toString);
    	classes.setPlaceholder("Selectionner la classe");
    	classes.addValueChangeListener(event -> setContentByClasse(event.getValue()));
    	classes.setWidth("17em");
    	selections.add(classes, anneeScolaire, trimestre);
    	grid.setItems(service.findElevesByNiveau(niveauId));
    }
    
    private void setContentByClasse(Classe classe) {
    	if(classe == null) {
    		return;
    	}
    		
    	grid.setItems(service.findElevesByClasse(classe.getId()));
    }
    
    private HorizontalLayout getToolbar() {
    	
    	for(Niveau nivo: levels) {
    		niveauMapper.put(nivo.toString(), nivo);
    		niveaux.add(new Tab(nivo.toString()));
    	}
    	
    	setTabContent(niveaux.getSelectedTab());
    	niveaux.addThemeVariants(TabsVariant.LUMO_EQUAL_WIDTH_TABS);
    	niveaux.addSelectedChangeListener(event ->
    		setTabContent(event.getSelectedTab())
    	);
    	niveaux.setSizeFull();
    	Label topLabel = new Label("Niveaux: ");
    	topLabel.getStyle().set("font-weight", "bold");
    	
        HorizontalLayout toolbar = new HorizontalLayout(topLabel, niveaux);
        toolbar.setAlignItems(Alignment.BASELINE);
        return toolbar;
    }
    
    private void displayBulletin(Eleve eleve) {
    	
    	
    	if(eleve == null)
    		closeBulletin();
    	else {
    		logger.info("Eleve selected: "+eleve.toString());
    		//logger.info("Nbre de cours: "+eleve.getCours().size());
    		bulletin.setEleve(eleve);
    		bulletin.setNotes(service.findNotesByEleve(eleve.getId()));
    		bulletin.setVisible(true);
    		
    		addClassName("bulletin");
    		
    	}
    }
    
    private void exportBulletinPDF(BulletinView.SaveEvent event) {
    	
    }
    
    private void printBulletin(BulletinView.PrintEvent event) {
    	
    }

    private void closeBulletin() {
    	bulletin.setEleve(null);
        bulletin.setVisible(false);
        removeClassName("bulletin");
    }

}
