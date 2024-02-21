package cz.sedy.contacts;

import cz.sedy.contacts.model.ContactsUpdateCommand;
import cz.sedy.contacts.model.ExecutionPlan;
import cz.sedy.contacts.repository.ContactsRepository;
import cz.sedy.contacts.service.InDatabaseFilterContactsService;
import cz.sedy.contacts.service.InMemoryFilterContactsService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class ContactsServicePerformanceTest {

    private static ContactsRepository contactsRepository;

    private static final UUID EXISTING_USER_ID = UUID.fromString("46ef12a6-e538-4dc6-b4c1-690102f73a8c");
    @Autowired
    void setContactsRepository(ContactsRepository contactsRepository){
        ContactsServicePerformanceTest.contactsRepository = contactsRepository;
    }

    @Test
    public void executeJmhRunner() throws RunnerException {
        Options jmhRunnerOptions = new OptionsBuilder()
                .include("\\." + this.getClass().getSimpleName() + "\\.")
                .warmupIterations(1)
                .measurementIterations(3)
                .forks(0)
                .threads(1)
                .shouldDoGC(true)
                .shouldFailOnError(true)
                .resultFormat(ResultFormatType.JSON)
                .result("/dev/null")
                .shouldFailOnError(true)
                .jvmArgs("-server")
                .build();

        new Runner(jmhRunnerOptions).run();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void benchmarkInDatabase(ExecutionPlan executionPlan) {
        InDatabaseFilterContactsService inDatabaseFilterContactsService = new InDatabaseFilterContactsService(contactsRepository);
        List<String> contacts = new LinkedList<>();
        for (int i = 0; i < executionPlan.contacts; i++) {
            contacts.add(UUID.randomUUID().toString());
        }
        inDatabaseFilterContactsService.updateContacts(new ContactsUpdateCommand(
                UUID.randomUUID(),
                contacts
        ));
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void benchmarkInMemory(ExecutionPlan executionPlan) {
        InMemoryFilterContactsService inMemoryFilterContactsService = new InMemoryFilterContactsService(contactsRepository);
        List<String> contacts = new LinkedList<>();
        for (int i = 0; i < executionPlan.contacts; i++) {
            contacts.add(UUID.randomUUID().toString());
        }
        inMemoryFilterContactsService.updateContacts(new ContactsUpdateCommand(
                UUID.randomUUID(),
                contacts
        ));
    }
}