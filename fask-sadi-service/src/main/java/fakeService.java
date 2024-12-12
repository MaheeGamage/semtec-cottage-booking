
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class fakeService
 */
@WebServlet("/fakeService")
public class fakeService extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// Group 3
	private static final String rdgFilePath = "/res/Group03/RDG.xml";
	private static final String rrgFilePath = "/res/Group03/RRG.xml";
	
	// Group 8
//	private static final String rdgFilePath = "/res/Group08/cottageSSWAPServiceRDG.xml";
//	private static final String rrgFilePath = "/res/Group08/cottageSSWAPServiceRRG.rdf";

	/**
	 * Default constructor.
	 */
	public fakeService() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());

		response.setContentType("application/rdf+xml");
		response.setCharacterEncoding("UTF-8");

		// Path to the RDF file
		String rdfFilePath = getServletContext().getRealPath(rdgFilePath);
		File rdfFile = new File(rdfFilePath);

		if (!rdfFile.exists()) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "RDF file not found.");
			return;
		}

		// Write the RDF file to the response
		try (FileInputStream fis = new FileInputStream(rdfFile); OutputStream out = response.getOutputStream()) {
			byte[] buffer = new byte[1024];
			int bytesRead;
			while ((bytesRead = fis.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.setContentType("application/rdf+xml");
		response.setCharacterEncoding("UTF-8");

		// Path to the RDF file
		String rdfFilePath = getServletContext().getRealPath(rrgFilePath);
		File rdfFile = new File(rdfFilePath);

		if (!rdfFile.exists()) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "RDF file not found.");
			return;
		}

		// Write the RDF file to the response
		try (FileInputStream fis = new FileInputStream(rdfFile); OutputStream out = response.getOutputStream()) {
			byte[] buffer = new byte[1024];
			int bytesRead;
			while ((bytesRead = fis.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
			}
		}
	}

}
