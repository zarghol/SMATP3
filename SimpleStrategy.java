package SMATP3;

public class SimpleStrategy implements ThinkingStragegy {

	@Override
	public void reflexionAction(Snapshot snapshot, Agent agent) {
		if (!agent.handlePostOffice()) {
			Direction toFollow = Direction.directionDifferential(agent.getPosition(), agent.getAimPosition());
			Position newPosition = agent.getPosition().towardDirection(toFollow);
			// FIXME: utiliser un agent.getSnapshot ?
			if (snapshot.isPositionOccupied(newPosition)) {
				agent.sendMessage(snapshot.getAgentId(newPosition));
			} else {
				//this.grid.moveAgent(agent.getPosition(), newPosition);
			}
		}
	}

	@Override
	public String getName() {
		return "SimpleStrategy";
	}

}
