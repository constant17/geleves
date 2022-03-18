package com.geleves.app.views;

import javax.annotation.security.PermitAll;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@Component
@Scope("prototype")
@Route(value="paiements", layout = MainLayout.class)
@PageTitle("Paiement | Gélèves")
@PermitAll
public class PaiementView extends VerticalLayout {
	
	public PaiementView() {
		MainLayout.tabs.setSelectedIndex(MainLayout.PAIEMENT_TAB);
	}

}
