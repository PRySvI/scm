package cm.interfaces;

import cm.objects.Personne;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by Святослав on 22.12.2017.
 */
public interface PersonneMenager {
    

      void  addPersonne(Personne p);
      void  deletePersonnes(ObservableList<Personne> p);
      void  updatePersonne(Personne p);
}
