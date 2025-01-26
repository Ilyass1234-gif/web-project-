
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static List<Stagiaire> stagiaires = new ArrayList<>();
    private static List<Stage> stages = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean continuer = true;
        while (continuer) {
            afficherMenu();
            int choix = scanner.nextInt();
            scanner.nextLine();
            switch (choix) {
                case 1:
                    ajouterStagiaire();
                    break;
                case 2:
                    supprimerStagiaire();
                    break;
                case 3:
                    afficherStagiaires();
                    break;
                case 4:
                    ajouterStage();
                    break;
                case 5:
                    associerStagiaireStage();
                    break;
                case 6:
                    afficherStages();
                    break;
                case 7:
                    rechercherStagiaire();
                    break;
                case 8:
                    continuer = false;
                    break;
                default:
                    System.out.println("Choix invalide.");
            }
        }
    }

    private static void afficherMenu() {
        System.out.println("\n1. Ajouter un stagiaire");
        System.out.println("2. Supprimer un stagiaire");
        System.out.println("3. Afficher tous les stagiaires");
        System.out.println("4. Ajouter un stage");
        System.out.println("5. Associer un stagiaire à un stage");
        System.out.println("6. Afficher tous les stages avec les stagiaires associés");
        System.out.println("7. Rechercher un stagiaire");
        System.out.println("8. Quitter");
        System.out.print("Choisissez une option: ");
    }

    private static void ajouterStagiaire() {
        System.out.print("Nom du stagiaire: ");
        String nom = scanner.nextLine();
        System.out.print("Prénom du stagiaire: ");
        String prenom = scanner.nextLine();
        System.out.print("Niveau d'étude: ");
        String niveau = scanner.nextLine();
        stagiaires.add(new Stagiaire(nom, prenom, niveau));
        System.out.println("Stagiaire ajouté.");
    }

    private static void supprimerStagiaire() {
        System.out.print("Nom du stagiaire à supprimer: ");
        String nom = scanner.nextLine();
        stagiaires.removeIf(stagiaire -> stagiaire.getNom().equals(nom));
        System.out.println("Stagiaire supprimé.");
    }

    private static void afficherStagiaires() {
        if (stagiaires.isEmpty()) {
            System.out.println("Aucun stagiaire trouvé.");
        } else {
            for (Stagiaire stagiaire : stagiaires) {
                stagiaire.afficher();
            }
        }
    }

    private static void ajouterStage() {
        System.out.print("Titre du stage: ");
        String titre = scanner.nextLine();
        System.out.print("Durée du stage (en jours): ");
        int duree = scanner.nextInt();
        scanner.nextLine();
        stages.add(new Stage(titre, duree));
        System.out.println("Stage ajouté.");
    }

    private static void associerStagiaireStage() {
        System.out.print("Nom du stagiaire: ");
        String nom = scanner.nextLine();
        System.out.print("Titre du stage: ");
        String titre = scanner.nextLine();

        Stagiaire stagiaire = null;
        for (Stagiaire s : stagiaires) {
            if (s.getNom().equals(nom)) {
                stagiaire = s;
                break;
            }
        }

        Stage stage = null;
        for (Stage s : stages) {
            if (s.getTitre().equals(titre)) {
                stage = s;
                break;
            }
        }

        if (stagiaire != null && stage != null) {
            stage.ajouterStagiaire(stagiaire);
            System.out.println("Stagiaire associé au stage.");
        } else {
            System.out.println("Stagiaire ou stage introuvable.");
        }
    }

    private static void afficherStages() {
        if (stages.isEmpty()) {
            System.out.println("Aucun stage trouvé.");
        } else {
            for (Stage stage : stages) {
                stage.afficher();
            }
        }
    }

    private static void rechercherStagiaire() {
        System.out.print("Nom du stagiaire à rechercher: ");
        String nom = scanner.nextLine();
        boolean trouvé = false;
        for (Stagiaire stagiaire : stagiaires) {
            if (stagiaire.getNom().equals(nom)) {
                stagiaire.afficher();
                trouvé = true;
                break;
            }
        }
        if (!trouvé) {
            System.out.println("Stagiaire non trouvé.");
        }
    }
}

abstract class Personne {
    protected String nom;
    protected String prenom;

    public Personne(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
    }

    public abstract void afficher();
}

class Stagiaire extends Personne {
    private String niveauEtude;

    public Stagiaire(String nom, String prenom, String niveauEtude) {
        super(nom, prenom);
        this.niveauEtude = niveauEtude;
    }

    @Override
    public void afficher() {
        System.out.println("Nom: " + nom + ", Prénom: " + prenom + ", Niveau d'étude: " + niveauEtude);
    }

    public String getNom() {
        return nom;
    }
}

class Stage implements Gestion {
    private String titre;
    private int duree;
    private List<Stagiaire> stagiaires;

    public Stage(String titre, int duree) {
        this.titre = titre;
        this.duree = duree;
        this.stagiaires = new ArrayList<>();
    }

    @Override
    public void ajouter() {
        System.out.println("Utilisez ajouterStagiaire() pour ajouter un stagiaire spécifique.");
    }

    @Override
    public void supprimer() {
        System.out.println("Utilisez supprimerStagiaire() pour supprimer un stagiaire spécifique.");
    }

    @Override
    public void afficher() {
        System.out.println("Stage: " + titre + ", Durée: " + duree + " jours");
        if (stagiaires.isEmpty()) {
            System.out.println("Pas de stagiaires associés.");
        } else {
            System.out.println("Stagiaires associés:");
            for (Stagiaire stagiaire : stagiaires) {
                stagiaire.afficher();
            }
        }
    }

    public void ajouterStagiaire(Stagiaire stagiaire) {
        stagiaires.add(stagiaire);
    }

    public void supprimerStagiaire(String nom) {
        stagiaires.removeIf(stagiaire -> stagiaire.getNom().equals(nom));
    }

    public int nombreDeStagiaires() {
        return stagiaires.size();
    }

    public String getTitre() {
        return titre;
    }
}

interface Gestion {
    void ajouter();
    void supprimer();
    void afficher();
}
