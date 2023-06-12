package de.fhws.fiw.fds.suttondemoSql.server.database.utils;

import de.fhws.fiw.fds.suttondemoSql.server.database.DaoFactory;
import de.fhws.fiw.fds.suttondemoSql.server.models.Person;

import java.time.LocalDate;
import java.util.stream.IntStream;

public class InitializeDatabase {
	public static void initialize() {
		IntStream.range(0, 100).forEach( ind -> {
			Person person = new Person("max" + (ind == 0 ? "" : ind),
					"Müller",
					"maxmueller" + (ind == 0 ? "" : ind) + "@gmail.com",
					LocalDate.of(1970, 01, 01).plusMonths(ind));
			DaoFactory.getInstance().getPersonDao().create(person);
		});
	}
}