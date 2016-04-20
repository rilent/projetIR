package arbres;

import utils.EActionTank;

public class ElementTernaire implements ElementNoeud{

	EActionTank action;



	public ElementTernaire(EActionTank action) {
		super();
		this.action = action;

	}

	public EActionTank getAction() {
		return action;
	}




	public void setAction(EActionTank action) {
		this.action = action;
	}

	@Override
	public String toStringAffichage() {
		return action.toString();
	}

	@Override
	public String toStringMethod() {
		return action.toString();
	}

	

	
	
}
