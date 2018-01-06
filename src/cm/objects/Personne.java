package cm.objects;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

/**
 * Created by Святослав on 22.12.2017.
 */
public class Personne {
    String Nom, Prenom, Adresse, email, Emploi, Interesses,telephone1,telephone2,niveau,dernierInfos;
    int id;
    int age;
    Date dateNaissance;



    public Personne(int id, String nom, String prenom, String adresse, String email, String emploi, String interesses, Date dateNaissance, String telephone1, String telephone2, String niveau, String dernierInfos) {
        this.id = id;
        Nom = nom;
        Prenom = prenom;
        Adresse = adresse;
        this.email = email;
        Emploi = emploi;
        Interesses = interesses;
        this.dateNaissance = dateNaissance;
        this.telephone1 = telephone1;
        this.telephone2 = telephone2;
        this.niveau = niveau;
        this.dernierInfos = dernierInfos;
    }

    public String getDernierInfos() {
        return dernierInfos;
    }

    public void setDernierInfos(String dernierInfos) {
        this.dernierInfos = dernierInfos;
    }

    private void setId(int id) {
        this.id = id;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }
    public int getId() {
        return id;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getPrenom() {
        return Prenom;
    }

    public void setPrenom(String prenom) {
        Prenom = prenom;
    }

    public String getAdresse() {
        return Adresse;
    }

    public void setAdresse(String adresse) {
        Adresse = adresse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmploi() {
        return Emploi;
    }

    public void setEmploi(String emploi) {
        Emploi = emploi;
    }

    public String getInteresses() {
        return Interesses;
    }

    public void setInteresses(String interesses) {
        Interesses = interesses;
    }

    public int getAge() {
        return Period.between(LocalDate.parse(this.getDateNaissance().toString()), LocalDate.now()).getYears();
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getTelephone1() {
        return telephone1;
    }

    public void setTelephone1(String telephone1) {
        this.telephone1 = telephone1;
    }

    public String getTelephone2() {
        return telephone2;
    }

    public void setTelephone2(String telephone2) {
        this.telephone2 = telephone2;
    }



}
