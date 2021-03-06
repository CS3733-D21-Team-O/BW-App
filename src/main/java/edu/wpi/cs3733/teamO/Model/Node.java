package edu.wpi.cs3733.teamO.Model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.cs3733.teamO.Database.NodesAndEdges;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Hashtable;

public class Node extends RecursiveTreeObject<Node> implements Comparable<Node> {
  String ID, building, nodeType, longName, shortName, floor, team;
  int xCoord, yCoord;
  private boolean visible;

  // added from GraphNode
  private HashSet<Node> neighbourList;
  private Hashtable<Node, Edge> nodeEdgeHashtable;
  private double priority;
  private boolean visited;

  /**
   * Constructor for Node
   *
   * @param ID
   * @param xCoord
   * @param yCoord
   * @param floor
   * @param building
   * @param nodeType
   * @param longName
   * @param shortName
   * @param team
   */
  public Node(
      String ID,
      int xCoord,
      int yCoord,
      String floor,
      String building,
      String nodeType,
      String longName,
      String shortName,
      String team) {
    this.ID = ID;
    this.building = building;
    this.nodeType = nodeType;
    this.longName = longName;
    this.shortName = shortName;
    this.floor = floor;
    this.xCoord = xCoord;
    this.yCoord = yCoord;
    this.team = team;
    this.visited = false;
    this.neighbourList = new HashSet<Node>();
    this.nodeEdgeHashtable = new Hashtable<>();
    this.visible = true;
  }

  public Node(
      String ID,
      int xCoord,
      int yCoord,
      String floor,
      String building,
      String nodeType,
      String longName,
      String shortName,
      String team,
      boolean visible) {
    this.ID = ID;
    this.building = building;
    this.nodeType = nodeType;
    this.longName = longName;
    this.shortName = shortName;
    this.floor = floor;
    this.xCoord = xCoord;
    this.yCoord = yCoord;
    this.team = team;
    this.visited = false;
    this.neighbourList = new HashSet<>();
    this.visible = visible;
    this.nodeEdgeHashtable = new Hashtable<>();
  }

  public Node() {
    this.ID = null;
    this.building = null;
    this.nodeType = null;
    this.longName = null;
    this.shortName = null;
    this.floor = null;
    this.xCoord = 0;
    this.yCoord = 0;
    this.team = null;
    this.visited = false;
    this.neighbourList = new HashSet<>();
    this.visible = true;
    this.nodeEdgeHashtable = new Hashtable<>();
  }

  // for testing
  public Node(String nodeID, int x, int y) {
    this.ID = nodeID;
    this.xCoord = x;
    this.yCoord = y;
    this.visited = false;
    this.neighbourList = new HashSet<>();
  }

  public int compareTo(Node node) {
    return Double.compare(priority, node.getPriority());
  }

  public void addNeighbour(Node graphNode, Edge edge) {
    this.neighbourList.add(graphNode);
    this.nodeEdgeHashtable.put(graphNode, edge);
  }

  public String getTeam() {
    return team;
  }

  public void setTeam(String team) {
    this.team = team;
  }

  public HashSet<Node> getNeighbourList() {
    return neighbourList;
  }

  public void setNeighbourList(HashSet<Node> neighbourList) {
    this.neighbourList = neighbourList;
  }

  public double getPriority() {
    return priority;
  }

  public void setPriority(double priority) {
    this.priority = priority;
  }

  public boolean isVisited() {
    return visited;
  }

  public void setVisited(boolean visited) {
    this.visited = visited;
  }

  public String getID() {
    return ID;
  }

  public void setID(String ID) {
    this.ID = ID;
  }

  public String getBuilding() {
    return building;
  }

  public void setBuilding(String building) {
    this.building = building;
  }

  public String getNodeType() {
    return nodeType;
  }

  public void setNodeType(String nodeType) {
    this.nodeType = nodeType;
  }

  public String getLongName() {
    return longName;
  }

  public void setLongName(String longName) {
    this.longName = longName;
  }

  public String getShortName() {
    return shortName;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
  }

  public String getFloor() {
    return floor;
  }

  public void setFloor(String floor) {
    this.floor = floor;
  }

  public int getXCoord() {
    return xCoord;
  }

  public void setXCoord(int xCoord) {
    this.xCoord = xCoord;
  }

  public int getYCoord() {
    return yCoord;
  }

  public void setYCoord(int yCoord) {
    this.yCoord = yCoord;
  }

  public boolean isVisible() {
    return visible;
  }

  public void setVisible(boolean visible) {
    this.visible = visible;
  }

  public Hashtable<Node, Edge> getNodeEdgeHashtable() {
    return nodeEdgeHashtable;
  }

  public void setNodeEdgeHashtable(Hashtable<Node, Edge> nodeEdgeHashtable) {
    this.nodeEdgeHashtable = nodeEdgeHashtable;
  }

  public double getDistToNeighbour(Node neighbour) {
    if (nodeEdgeHashtable.containsKey(neighbour)) {
      Edge e = nodeEdgeHashtable.get(neighbour);
      return e.getLength();
    } else {
      return -1;
    }
  }

  public void editNode(
      int x,
      int y,
      String sFloor,
      String building,
      String nodeType,
      String longName,
      String shortName,
      boolean visible)
      throws SQLException {
    this.xCoord = x;
    this.yCoord = y;
    this.building = building;
    // TODO: if node type is changed -> change ID? (would also have to change stuff in graph then)
    this.nodeType = nodeType;
    this.longName = longName;
    this.shortName = shortName;
    this.visible = visible;

    NodesAndEdges.editNode(
        this.getID(),
        this.getXCoord(),
        this.getYCoord(),
        this.getFloor(),
        this.getBuilding(),
        this.getNodeType(),
        this.getLongName(),
        this.getShortName(),
        "O",
        this.isVisible());
  }
}
