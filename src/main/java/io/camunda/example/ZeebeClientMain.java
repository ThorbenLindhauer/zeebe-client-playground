package io.camunda.example;

import java.net.URI;
import java.net.URISyntaxException;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.command.MigrationPlan;
import io.camunda.zeebe.client.api.response.MigrateProcessInstanceResponse;
import io.camunda.zeebe.client.api.response.Topology;

public class ZeebeClientMain {

	public static void main(String[] args) throws URISyntaxException {

		long processInstanceKey = 9007199254741141L;
		long targetDefinitionKey = 2251799813686980L;

		MigrationPlan plan = MigrationPlan.newBuilder().withTargetProcessDefinitionKey(targetDefinitionKey)
				.addMappingInstruction("wait-state", "wait-state")
				.addMappingInstruction("Event_02bjeax", "Event_02bjeax").build();
		try (ZeebeClient client = ZeebeClient.newClientBuilder().usePlaintext().build()) {

			Topology topology = client.newTopologyRequest().send().join();
			System.out.println(topology);

			MigrateProcessInstanceResponse response = client.newMigrateProcessInstanceCommand(processInstanceKey)
					.migrationPlan(plan).send().join();

			System.out.println(response);
		}
	}

}
