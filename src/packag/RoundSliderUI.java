package packag;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JSlider;
import javax.swing.plaf.basic.BasicSliderUI;

public class RoundSliderUI extends BasicSliderUI {
    // Constructeur : initialise l'UI personnalisée pour le JSlider

    public RoundSliderUI(JSlider slider) {
        super(slider); // Appelle le constructeur de la classe de base BasicSliderUI
    }

    // Surcharge de la méthode paintThumb pour dessiner un curseur rond personnalisé
    @Override
    public void paintThumb(Graphics g) {
        int w = 18; // Largeur du curseur
        int h = 18; // Hauteur du curseur
        // Calcule la position pour centrer le curseur dans la zone prévue
        int x = thumbRect.x + (thumbRect.width - w) / 2;
        int y = thumbRect.y + (thumbRect.height - h) / 2;
        g.setColor(Color.white); // Définit la couleur de remplissage en blanc
        g.fillOval(x, y, h, h); // Dessine un ovale rempli (le curseur)
        g.setColor(Color.GRAY); // Définit la couleur du contour en gris
        g.drawOval(x, y, h, h); // Dessine le contour de l'ovale
    }

    // Surcharge de la méthode getThumbSize pour définir la taille du curseur
    @Override
    protected Dimension getThumbSize() {
        return new Dimension(18, 18); // Retourne une dimension fixe de 18x18 pixels
    }
}
