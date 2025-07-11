package packag;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import javax.swing.ImageIcon;
import javax.swing.JButton;

// Classe personnalisée pour créer un bouton rond avec une icône
public class RoundButton extends JButton {

    // Constructeur : initialise le bouton avec une icône et désactive les effets standards
    RoundButton(ImageIcon icon) {
        super(icon);
        setContentAreaFilled(false);// Désactive le fond par défaut du bouton
        setFocusPainted(false);      // Désactive l'affichage du focus
        setBorderPainted(false);     // Désactive l'affichage de la bordure
        setOpaque(false);            // Rend le bouton transparent

    }

    // Redéfinition de la méthode de dessin du composant pour le rendre rond
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2D = (Graphics2D) g.create();
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);// Lissage des bords
        int diameter = Math.min(getWidth(), getHeight()); // Détermine le diamètre du cercle
        Shape circle = new Ellipse2D.Double(0, 0, diameter, diameter); // Crée un cercle
        g2D.setColor(Color.black);
        g2D.fill(circle); // Remplit le cercle en noir
        g2D.setColor(Color.white);
        g2D.draw(circle); // Dessine le contour du cercle en blanc
        super.paintComponent(g); // Dessine l'icône du bouton
        g2D.dispose(); // Libère les ressources graphiques

    }
}
