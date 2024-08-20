package fr.isen.perigot.educscan.ui.dashboard;

public class Presences {
        private String idApprenant;
        private String heureArrivee;

        public Presences(String idApprenant, String heureArrivee) {
                this.idApprenant = idApprenant;
                this.heureArrivee = heureArrivee;
        }

        public String getIdApprenant() {
                return idApprenant;
        }

        public void setIdApprenant(String idApprenant) {
                this.idApprenant = idApprenant;
        }

        public String getHeureArrivee() {
                return heureArrivee;
        }

        public void setHeureArrivee(String heureArrivee) {
                this.heureArrivee = heureArrivee;
        }
}

