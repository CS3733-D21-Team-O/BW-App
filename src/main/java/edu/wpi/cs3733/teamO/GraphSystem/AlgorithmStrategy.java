package edu.wpi.cs3733.teamO.GraphSystem;

import edu.wpi.cs3733.teamO.model.Node;
import java.util.ArrayList;

interface AlgorithmStrategy {

  ArrayList<Node> findPath(Node startNode, Node targetNode);
}
