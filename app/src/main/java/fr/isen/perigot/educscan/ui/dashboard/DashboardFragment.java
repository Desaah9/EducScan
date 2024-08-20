package fr.isen.perigot.educscan.ui.dashboard;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.Locale;
import java.util.Calendar;

import fr.isen.perigot.educscan.R;
import fr.isen.perigot.educscan.databinding.FragmentDashboardBinding;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;

public class DashboardFragment extends Fragment {

    //stockage du username de l'utilisateur
    private String currentUserUsername;

    // Déclaration des variables pour le chronomètre
    private TextView countdownTimer;
    private long timeLeftInMillis;
    private CountDownTimer countDownTimer;
    private boolean timerRunning;
    private FragmentDashboardBinding binding;

    @SuppressLint("CutPasteId")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //recuperation du username (a changer pour l'id étudiant dans une version finale)
        currentUserUsername = getActivity().getIntent().getStringExtra("username");

        //initialiser le QRcode QR code dynamique avec info de firebase :
        dynamiqueQRCode();

        // Initialisation du TextView pour le chronomètre
        countdownTimer = root.findViewById(R.id.text_timer);
        // Durée du chrono en millisecondes (ici, 10 secondes)
        timeLeftInMillis = 10000;
        // Démarrer le chronomètre
        startTimer();

        return root;
    }

    public void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountdownText();
            }

            @Override
            public void onFinish() {
                //QR code dynamique avec info de firebase :
                dynamiqueQRCode();
                timeLeftInMillis = 10000;
                startTimer();
            }
        }.start();
        timerRunning = true;
    }

    // Méthode pour mettre à jour le texte du chronomètre
    public void updateCountdownText() {
        int seconds = (int) (timeLeftInMillis / 1000);
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", seconds / 60, seconds % 60);
        countdownTimer.setText(timeLeftFormatted);
    }

    // Creation d'un QRcode statique
    public void generateQRCode(String text) {
        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, 512, 512);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            // Utilisation du View Binding pour accéder à l'ImageView du fragment_dashboard.xml
            binding.imageViewQrCode.setImageBitmap(bmp);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    public Handler handler = new Handler();
    public Runnable updateQRCodeRunnable;
        public void dynamiqueQRCode() {
        updateQRCodeRunnable = new Runnable() {
            @Override
            public void run() {
                // Obtenir l'heure actuelle
                Calendar calendrier = Calendar.getInstance();
                int heure = calendrier.get(Calendar.HOUR_OF_DAY);
                int minute = calendrier.get(Calendar.MINUTE);
                int seconde = calendrier.get(Calendar.SECOND);
                // Formatage de l'heure
                @SuppressLint("DefaultLocale") String heureFormattee = String.format("%02d:%02d:%02d", heure, minute, seconde);

                // Générer le token JWT avec le username et l'heure
                String jwtToken = generateJWTToken(currentUserUsername, heureFormattee);
                //metre a jour le QRcode avec le token contenant l'heure et le username en créant un Qrcode statique
                generateQRCode(jwtToken);
            }
        };
        // Lancer la première mise à jour
        handler.post(updateQRCodeRunnable);
    }

    // Méthode pour générer le token JWT
    private String generateJWTToken(String username, String heure) {

        //pour les test plus tard a créer sur le serveur autopresnece.isen.fr et a recup dans l'application une différentes pour chaque étudiant
        String secretKeyBase64 = "MZXW6YTBOIYW4LTPNZXQ5LQOJ5GYZTBOJUXGS3DJORUW63RANRUW4ZLSMNSGK3DF";

        // Date d'expiration du token (1 heure dans cet exemple)
        long expirationTime = 11000; // 11 secondes millisecondes
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);

        // Génération du token JWT
        String jwtToken = Jwts.builder()
                .setSubject(username) // Le sujet du token (ici, le nom d'utilisateur)
                .claim("heure", heure)
                .setExpiration(expirationDate) // Date d'expiration du token
                .signWith(SignatureAlgorithm.HS256, secretKeyBase64) // Signature du token avec HMAC-SHA256
                .compact();

        return jwtToken;
    }
}