package lorian.graph.android.opengl;

import android.content.Context;
import android.opengl.GLSurfaceView;


public class GraphSurfaceView extends GLSurfaceView {

	public GraphRenderer renderer;

	
	public GraphSurfaceView(Context context) {
		super(context);
		setEGLContextClientVersion(1);
		super.setEGLConfigChooser(8 , 8, 8, 8, 16, 0);
		renderer = new GraphRenderer(context);
	
		
		setRenderer(renderer);
		setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
	}
	/*
	public void recalculateWindowSettings()
	{
		renderer.recalculateWindowSettings();
	}
	public void recalculateFunctions()
	{
		renderer.recalculateFunctions();
	}
	*/
	public void setRenderAxes(boolean render)
	{
		renderer.render_axes = render;
	}
	public void setRenderFunctions(boolean render)
	{
		renderer.render_functions = render;
	}

}
