package com.geleves.app.views.list;

import com.geleves.app.data.entity.Contact;
import com.geleves.app.data.entity.Eleve;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ListDataProvider;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ListViewTest {

    @Autowired
    private ElevesListView listView;

    @Test
    public void formShownWhenContactSelected() {
        /*Grid<Eleve> grid = listView.grid;
        Eleve firstContact = getFirstItem(grid);

        ContactForm form = listView.form;

        Assert.assertFalse(form.isVisible());
        grid.asSingleSelect().setValue(firstContact);
        Assert.assertTrue(form.isVisible());
        Assert.assertEquals(firstContact.getPrenom(), form.prenom.getValue());*/
    }
    private Contact getFirstItem(Grid<Eleve> grid) {
        //return( (ListDataProvider<Eleve>) grid.getDataProvider()).getItems().iterator().next();
    	return null;
    }
}