package cm.interfaces.impls;

import cm.daemons.AniversaryChecker;
import cm.interfaces.PersonneMenager;
import cm.objects.Personne;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.Date;

/**
 * Created by Святослав on 22.12.2017.
 */
public class PersonneMenagerEngine implements PersonneMenager
{
    ObservableList<Personne> personnes = FXCollections.observableArrayList();

    public PersonneMenagerEngine() {
       loadPersonList();
    }

    @Override
    public void addPersonne(Personne p) {
        personnes.add(p);
        addNewPersonneToDB(p);
    }

    @Override
    public void deletePersonnes(ObservableList<Personne> personnesToRemove) {
        for (Personne pers:personnesToRemove
             ) {
            deletePersonneFromDB(pers);
        }
        this.personnes.removeAll(personnesToRemove);


    }

    @Override
    public void updatePersonne(Personne p) {
        updatePersonneInDb(p);

    }

    public ObservableList<Personne> getPersonnes(){
        return personnes;
    }


    public void deletePersonneFromDB(Personne personnetoDelete)
    {
        String sql = String.format("DELETE FROM personnes WHERE `Nom`='%s' AND `Prenom` = '%s' LIMIT 1",personnetoDelete.getNom(),personnetoDelete.getPrenom());
        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/scm","root", "root");
            Statement statement = connection.createStatement();
            ) {

            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void addNewPersonneToDB(Personne newPersonne)
    {
        String sql = String.format("INSERT INTO `personnes` ( `Nom`, `Prenom`, `Adresse`, `Email`, `Emploi`, `Interesses`, `date_de_naissance`, `telephone1`, `telephone2`, `niveau`,`derniers_infos`) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%tF',  '%s', '%s', '%s', '%s')",newPersonne.getNom(),newPersonne.getPrenom(),newPersonne.getAdresse(),newPersonne.getEmail(),newPersonne.getEmploi(),newPersonne.getInteresses(),newPersonne.getDateNaissance(),newPersonne.getTelephone1(),newPersonne.getTelephone2(),newPersonne.getNiveau(),newPersonne.getDernierInfos());
        try(
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/scm","root", "root");
                Statement statement = connection.createStatement();
                ) { statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updatePersonneInDb(Personne personnetoUpdate)
    {
        String sql = String.format("UPDATE `personnes` SET `Nom`='%s', `Prenom`='%s', `Adresse`='%s', `Email`='%s', `Emploi`='%s', `Interesses`='%s', `date_de_naissance`= '%tF', `telephone1`='%s', `telephone2`='%s', `niveau`='%s', `derniers_infos`='%s' WHERE (`id`='"+personnetoUpdate.getId()+"') ",personnetoUpdate.getNom(),personnetoUpdate.getPrenom(),personnetoUpdate.getAdresse(),personnetoUpdate.getEmail(),personnetoUpdate.getEmploi(),personnetoUpdate.getInteresses(),personnetoUpdate.getDateNaissance(),personnetoUpdate.getTelephone1(),personnetoUpdate.getTelephone2(),personnetoUpdate.getNiveau(),personnetoUpdate.getDernierInfos());
         try(
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/scm","root", "root");
        Statement statement = connection.createStatement())
         {
             statement.executeUpdate(sql);
         } catch (SQLException e) {
             e.printStackTrace();
         }
    }

    public void  loadPersonList(){

        personnes.clear();
        try (

                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/scm","root", "root");
                Statement stmnt = connection.createStatement();
                ResultSet rs = stmnt.executeQuery("select * from personnes")

        ){
            while (rs.next()) {
                int id = rs.getInt("id");
                String  Nom  = rs.getString("Nom");
                String  Prenom  = rs.getString("Prenom");
                String  Adresse  = rs.getString("Adresse");
                String email = rs.getString("Email");
                String Emploi  = rs.getString("Emploi");
                String  Interesses  = rs.getString("Interesses");
                Date dateDeNaissance = rs.getDate("date_de_naissance");
                String telephone1 = rs.getString("telephone1");
                String telephone2 = rs.getString("telephone2");
                String niveau  = rs.getString("niveau");
                String dernierInfos =  rs.getString("derniers_infos");
                personnes.add(new Personne(id,Nom,Prenom,Adresse,email,Emploi,Interesses,dateDeNaissance,telephone1,telephone2,niveau,dernierInfos));
            }
            System.out.println("loaded "+personnes.size()+" personnes");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
