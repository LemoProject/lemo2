package de.htwb.kappe.embeddedserver.rest;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//@Component
@Path("/r")
@Produces(MediaType.TEXT_PLAIN)
public class CopyOfHelloWorldResource {
//
//	@Reference
//	private EntityManager em;

	@GET
	@Path("/r1")
	public String sayHello() {
		String r = "Hello, CopyOfHelloWorldResource em "  ;

		// TypedQuery<Foo> query = em.createQuery("SELECT c FROM Foo c", Foo.class);
		// List<Foo> results = query.getResultList();
		// for (Foo foo : results) {
		// r+= foo.getText();
		// }
//		r += " " + getClass().getResource("/templates/form.mustache");

		return r;
	}

	// @Template(name = "/templates/form.mustache")
//	@GET
//	@Path("/r2")
//	@Produces(MediaType.TEXT_HTML)
//	public Viewable sayHello2() {
//		String r = "Hello, sf em " ;

		// TypedQuery<Foo> query = em.createQuery("SELECT c FROM Foo c", Foo.class);
		// List<Foo> results = query.getResultList();
		// for (Foo foo : results) {
		// r+= foo.getText();
		// }
		// Viewable viewable;
		// ClassLoader myClassLoader = getClass().getClassLoader();
		// ClassLoader originalContextClassLoader = Thread.currentThread().getContextClassLoader();
		// try {
		// Thread.currentThread().setContextClassLoader(myClassLoader);
		// viewable =
		// } finally {
		// Thread.currentThread().setContextClassLoader(originalContextClassLoader);
		// }

//		return new Viewable("/templates/form.mustache");
//	}

	@POST
	@Path("/r")
	public String compute(@FormParam("foo") String foo) {

		return "foo: " + foo;
	}

}