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

import com.geleves.app.data.entity.Classe;
import com.geleves.app.data.entity.Eleve;
import com.geleves.app.data.entity.Niveau;
import com.geleves.app.data.entity.Periode;
import com.geleves.app.data.service.GelevesService;
import com.geleves.app.views.list.NotesForm;
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
@Route(value="notes", layout = MainLayout.class)
@PageTitle("Notes | Gélèves")
@PermitAll
public class NotesView extends VerticalLayout{
	
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
	NotesForm notesForm;
	Logger logger;
	
    GelevesService service;

    public NotesView(GelevesService service) {
    	MainLayout.tabs.setSelectedIndex(MainLayout.NOTES_TAB);
    	logger = LoggerFactory.getLogger(getClass());
        this.service = service;
        levels = service.findAllNiveaux();
        periodes = service.findAllPeriodes();
        
        notesForm = new NotesForm();
        notesForm.setWidth("22em");
        //notesForm.addListener(BulletinView.SaveEvent.class, this::exportBulletinPDF);
        //notesForm.addListener(BulletinView.PrintEvent.class, this::printBulletin);
        //notesForm.addListener(BulletinView.CloseEvent.class, e -> closeBulletin());
        
        addClassName("list-view"); 
        setSizeFull();
        configureGrid();
        FlexLayout content = new FlexLayout(grid, notesForm);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, notesForm);
        content.setFlexShrink(0, notesForm);
        content.addClassNames("content", "gap-m");
        content.setSizeFull();
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
    		// closeNotesForm();
    		return;
    	else {
    		logger.info("Eleve selected: "+eleve.toString());
    		//logger.info("Nbre de cours: "+eleve.getCours().size());
    		notesForm.setEleve(eleve);
    		//notesForm.setNotes(service.findNotesByEleve(eleve.getId()));
    		notesForm.setVisible(true);
    		
    		addClassName("notesForm");
    		
    	}
    }
    
    private void exportBulletinPDF(BulletinView.SaveEvent event) {
    	
    }
    
    private void printBulletin(BulletinView.PrintEvent event) {
    	
    }

    private void closeNotesForm() {
    	notesForm.setEleve(null);
        notesForm.setVisible(false);
        removeClassName("notesForm");
    }

}
