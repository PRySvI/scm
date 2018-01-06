package cm.daemons;

import cm.interfaces.PersonneMenager;
import cm.interfaces.impls.PersonneMenagerEngine;
import cm.objects.Personne;
import cm.start.Main;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import org.controlsfx.control.Notifications;

import javax.management.Notification;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * Created by Святослав on 05.01.2018.
 */
public class AniversaryChecker extends Thread{

    ObservableList<Personne> personnes ;
    Calendar calendarToday, calendarOfPersonne;

    public AniversaryChecker() {

        this.setDaemon(true);
    }

    private void checkAniversary()
    {
        this.personnes = Main.personneMenagerEngine.getPersonnes();
        System.out.println("cheking personnes"+personnes.size());
        calendarToday = Calendar.getInstance();
        int dayToday, monthToday;
        for (Personne personne:personnes)
        {
            calendarOfPersonne =Calendar.getInstance();
            calendarOfPersonne.setTime(personne.getDateNaissance());
            if(calendarToday.get(Calendar.MONTH)==calendarOfPersonne.get(Calendar.MONTH))
            {
                if(calendarToday.get(Calendar.DATE)==calendarOfPersonne.get(Calendar.DATE)) {
                    System.out.println("OK");
                    Platform.runLater(new Runnable() {

                        @Override
                        public void run() {
                            Notifications.create().title("Anniveraire de " + personne.getNom()).text("C'est Aujaurdhui").show();
                        }
                    });
                }

            }

        }
    }

    @Override
    public void run() {
        while (1>0){
            checkAniversary();
            try {
                TimeUnit.MINUTES.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
