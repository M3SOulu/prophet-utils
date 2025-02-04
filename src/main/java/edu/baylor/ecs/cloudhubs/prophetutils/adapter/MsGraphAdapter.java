package edu.baylor.ecs.cloudhubs.prophetutils.adapter;

import edu.baylor.ecs.cloudhubs.prophetdto.mermaid.MermaidEdge;
import edu.baylor.ecs.cloudhubs.prophetdto.mermaid.MermaidNode;
import edu.baylor.ecs.cloudhubs.prophetdto.mermaid.ms.MsMermaidGraph;
import edu.baylor.ecs.cloudhubs.prophetdto.mscontext.MsEdge;
import edu.baylor.ecs.cloudhubs.prophetdto.mscontext.MsModel;

import java.util.List;
import java.util.stream.Collectors;

public class MsGraphAdapter {
    /**
     * Builds a mermaid representation of a microservice communication graph
     * @param model
     * @return
     */
    public static MsMermaidGraph getMermaidGraphFromMsModel(MsModel model) {
        // nodes and edges in the model correspond nicely to mermaid nodes and edges
        List<MermaidNode> mermaidNodes = model.getNodes().stream().map(node -> new MermaidNode(node.getLabel())).collect(Collectors.toList());
        List<MermaidEdge> mermaidEdges = model.getEdges().stream()
                .map(edge -> new MermaidEdge(edge.getFrom().getLabel(), edge.getTo().getLabel(), getMsEdgeDescription(edge)))
                .collect(Collectors.toList());

        return new MsMermaidGraph(mermaidNodes, mermaidEdges, model);
    }

    /**
     * Helper to create a string description from an MsEdge
     * @param edge
     * @return A description that will show up in the mermaid graph
     */
    private static String getMsEdgeDescription(MsEdge edge) {
        return "HTTP Verb: " + edge.getLabel().getType() +
                "<br/>Arguments: " + escapeMermaidQuotes(edge.getLabel().getArgument()) +
                "<br/>Return: " + escapeMermaidQuotes(edge.getLabel().getMsReturn()) +
                "<br/>Endpoint function: " + escapeMermaidQuotes(edge.getLabel().getEndpointFunction());
    }

    /**
     * Replace double quotes with mermaid's escape sequence #quot;
     * @param input
     * @return
     */
    private static String escapeMermaidQuotes(String input) {
    	if (input == null) {
    		return "N/A";
    	}
        return input.replaceAll("\"", "#quot;");
    }
}
