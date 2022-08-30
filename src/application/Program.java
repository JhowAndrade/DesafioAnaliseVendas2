package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import entitites.Sale;

public class Program {

	public static void main(String[] args) {

		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);

		System.out.print("Entre com o caminho do arquivo: ");
		String path = sc.nextLine();

		try (BufferedReader br = new BufferedReader(new FileReader(path))) {

			List<Sale> list = new ArrayList<>();

			String line = br.readLine();
			while (line != null) {

				String[] fields = line.split(",");
				int month = Integer.parseInt(fields[0]);
				int year = Integer.parseInt(fields[1]);
				int items = Integer.parseInt(fields[3]);
				double total = Double.parseDouble(fields[4]);

				list.add(new Sale(month, year, fields[2], items, total));

				line = br.readLine();

			}

			Set<String> sellers = list.stream().map(x -> x.getSeller()).collect(Collectors.toSet());

			Map<String, Double> nameSeller = new HashMap<>();

			for (String name : sellers) {
				
				double sum = list.stream()
						.filter((x -> x.getSeller().toUpperCase().matches(name.toUpperCase())))
						.map(x -> x.getTotal())
						.reduce(0.0, (x, y) -> x + y);

				nameSeller.put(name, sum);
			}

			System.out.println();
			System.out.println("Total de vendas por vendedor:");
			nameSeller.forEach((x, y) -> System.out.printf("%s - R$ %.2f%n", x, y));

		} catch (IOException e) {
			System.out.println("Erro: " + e.getMessage());

		}

		sc.close();

	}

}
