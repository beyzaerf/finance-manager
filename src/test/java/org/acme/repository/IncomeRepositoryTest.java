package org.acme.repository;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

@QuarkusTest
@Transactional
public class IncomeRepositoryTest {

  @Inject
  IncomeRepository incomeRepository;

  @Test
  public void testFindByDescription() {

  }

  @Test
  public void testSortByAmount() {

  }
}
