package taskbar;

import javax.swing.SwingUtilities;

public class Controller {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                new GUI().createAndShowUI();
            }
        });
	}

}
