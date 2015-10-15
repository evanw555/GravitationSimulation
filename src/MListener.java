import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MListener implements MouseListener{
	public static final int PAUSE = 0, CLEAR = 1, CREATE_DUST = 2;
	private int id;
	
	public MListener(int id){
		this.id = id;
	}
	
	public void mouseClicked(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mousePressed(MouseEvent e){
		switch(id){
		case PAUSE:
			Refs.sim.setPlaying(!Refs.sim.isPlaying());
			break;
		case CLEAR:
			Refs.sim.getEntityHandler().clear();
			break;
		case CREATE_DUST:
			Refs.sim.getEntityHandler().setCreateDust();
		}
	}
	public void mouseReleased(MouseEvent e){}
}
