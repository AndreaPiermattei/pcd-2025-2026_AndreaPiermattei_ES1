package pcd.mainApplicationAssignmentOne.view;

import pcd.mainApplicationAssignmentOne.controller.MainLoop;

public class View {

	private ViewFrame frame;
	private ViewModel viewModel;

	public View(ViewModel model, int w, int h) {
		frame = new ViewFrame(model, null, w, h);	
		frame.setVisible(true);
		this.viewModel = model;
	}

	public View(ViewModel model, int w, int h, MainLoop controller) {
		frame = new ViewFrame(model, controller, w, h);	
		frame.setVisible(true);
		this.viewModel = model;
	}
		
	public void render() {
		frame.render();
	}
	
	public ViewModel getViewModel() {
		return viewModel;
	}
}
