package assignment4;

import glWrapper.GLHalfedgeStructure;

import java.io.IOException;

import meshes.HalfEdgeStructure;
import meshes.WireframeMesh;
import meshes.exception.DanglingTriangleException;
import meshes.exception.MeshNotOrientedException;
import meshes.reader.ObjReader;
import openGL.MyDisplay;
import openGL.gl.GLDisplayable;
import sparse.CSRMatrix;


/**
 * Smoothing
 * @author Alf
 *
 */
public class Assignment4_2_smoothing {

	public static void main(String[] args) throws IOException, MeshNotOrientedException, DanglingTriangleException {
		WireframeMesh mesh = ObjReader.read("objs/bunny5k.obj", false);
		HalfEdgeStructure hs = new HalfEdgeStructure();
		hs.init(mesh);
		CSRMatrix m = LMatrices.mixedCotanLaplacian(hs);
		GLDisplayable glHsSmooth = LaplacianSmoother.smooth(hs, m, 0.01f);
		glHsSmooth.configurePreferredShader("shaders/trimesh_flat.vert",
				"shaders/trimesh_flat.frag", 
				"shaders/trimesh_flat.geom");
		
		m = LMatrices.mixedCotanLaplacian(hs);
		GLDisplayable glHsSharp = LaplacianSmoother.unsharpMasking(hs, m, 0.01f, 2.5f);
		glHsSharp.configurePreferredShader("shaders/trimesh_flat.vert",
				"shaders/trimesh_flat.frag", 
				"shaders/trimesh_flat.geom");
		
		
		//add initial mesh
		GLHalfedgeStructure glHs = new GLHalfedgeStructure(hs);
		glHs.configurePreferredShader("shaders/trimesh_flat.vert",
				"shaders/trimesh_flat.frag", 
				"shaders/trimesh_flat.geom");
		MyDisplay d = new MyDisplay();		
		d.addToDisplay(glHs);
		d.addToDisplay(glHsSmooth);
		d.addToDisplay(glHsSharp);
	}
}
