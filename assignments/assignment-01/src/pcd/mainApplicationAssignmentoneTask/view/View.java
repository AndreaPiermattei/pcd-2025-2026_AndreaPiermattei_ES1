package pcd.mainApplicationAssignmentoneTask.view;

import pcd.mainApplicationAssignmentoneTask.controller.MainLoopWithTask;

public class View {

	private ViewFrame frame;
	private ViewModel viewModel;

	public View(ViewModel model, int w, int h) {
		frame = new ViewFrame(model, null, w, h);	
		frame.setVisible(true);
		this.viewModel = model;
	}

	public View(ViewModel viewModel2, int w, int h, MainLoopWithTask controller) {
		frame = new ViewFrame(viewModel2, controller, w, h);	
		frame.setVisible(true);
		this.viewModel = viewModel2;
	}
		
	public void render() {
		frame.render();
	}
	
	public ViewModel getViewModel() {
		return viewModel;
	}
}
