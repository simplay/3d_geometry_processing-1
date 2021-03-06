package assignment1.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Iterator;

import meshes.HalfEdge;
import meshes.HalfEdgeStructure;
import meshes.Vertex;
import meshes.WireframeMesh;
import meshes.exception.DanglingTriangleException;
import meshes.exception.MeshNotOrientedException;
import meshes.reader.ObjReader;

import org.junit.Before;
import org.junit.Test;

public class VEIteratorTests {

	HalfEdgeStructure hs;
	
	@Before
	public void setUp() throws IOException {
		WireframeMesh n = ObjReader.read("./objs/oneNeighborhood.obj", true);
		hs = new HalfEdgeStructure();
		
		//create a half-edge structure out of the wireframe description.
		//As not every mesh can be represented as a half-edge structure
		//exceptions could occur.
		try {
			hs.init(n);
		} catch (MeshNotOrientedException | DanglingTriangleException e) {
			e.printStackTrace();
			return;
		}
		
	}
	
	@Test
	public void vertex0() {
		Vertex v = hs.getVertices().get(0);
		Iterator<HalfEdge> iter = v.iteratorVE();
		assertTrue(iter.hasNext());
		assertEquals("(5 --> 0)", iter.next().toString());
		assertTrue(iter.hasNext());
		assertEquals("(4 --> 0)", iter.next().toString());
		assertTrue(iter.hasNext());
		assertEquals("(3 --> 0)", iter.next().toString());
		assertTrue(iter.hasNext());
		assertEquals("(2 --> 0)", iter.next().toString());
		assertTrue(iter.hasNext());
		assertEquals("(1 --> 0)", iter.next().toString());
		assertFalse(iter.hasNext());
	}
	
	@Test
	public void vertex3() {
		Vertex v = hs.getVertices().get(3);
		Iterator<HalfEdge> iter = v.iteratorVE();
		assertTrue(iter.hasNext());
		assertEquals("(4 --> 3)", iter.next().toString());
		assertTrue(iter.hasNext());
		assertEquals("(2 --> 3)", iter.next().toString());
		assertTrue(iter.hasNext());
		assertEquals("(0 --> 3)", iter.next().toString());
		assertFalse(iter.hasNext());
	}
	
	@Test
	public void vertex5() {
		Vertex v = hs.getVertices().get(5);
		Iterator<HalfEdge> iter = v.iteratorVE();
		assertTrue(iter.hasNext());
		assertEquals("(1 --> 5)", iter.next().toString());
		assertTrue(iter.hasNext());
		assertEquals("(4 --> 5)", iter.next().toString());
		assertTrue(iter.hasNext());
		assertEquals("(0 --> 5)", iter.next().toString());
		
		assertFalse(iter.hasNext());
	}
}
