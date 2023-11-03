/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tugas2;

/**
 *
 * @author Kaho
 */
import java.util.*;

class Graph {
    private Map<String, List<City>> graph;

    public Graph() {
        graph = new HashMap<>();
    }

    public void addConnection(String city1, String city2, int distance) {
        /*
         * Metode ini digunakan untuk menambahkan koneksi antara dua kota ke dalam graf.
         * Juga memastikan bahwa keduanya ditambahkan sebagai tetangga satu sama lain.
         */
        graph.computeIfAbsent(city1, k -> new ArrayList<>()).add(new City(city2, distance));
        graph.computeIfAbsent(city2, k -> new ArrayList<>()).add(new City(city1, distance));
    }

    public List<City> getNeighbors(String city) {
        /*
         * Metode ini mengembalikan daftar kota tetangga dari suatu kota.
         * Jika kota tersebut tidak ditemukan dalam graf, mengembalikan daftar kosong.
         */
        return graph.getOrDefault(city, new ArrayList<>());
    }
}

class City {
    String name;
    int distance;

    public City(String name, int distance) {
        // Konstruktor digunakan untuk membuat objek kota dengan nama dan jarak ke kota tetangga.
        this.name = name;
        this.distance = distance;
    }
}

public class SearchAlgorithms {
    public static int gbfs(Graph graph, String start, String end) {
        // Inisialisasi openList sebagai PriorityQueue dengan perbandingan berdasarkan jarak (distance).
        PriorityQueue<City> openList = new PriorityQueue<>(Comparator.comparingInt(city -> city.distance));
        Set<String> visited = new HashSet();
        openList.add(new City(start, 0));

        while (!openList.isEmpty()) {
            City current = openList.poll();

            // Jika kota saat ini adalah tujuan, mengembalikan jarak ke tujuan.
            if (current.name.equals(end)) {
                return current.distance;
            }

            if (visited.contains(current.name)) {
                continue; // Lewati simpul yang sudah dikunjungi
            }
            visited.add(current.name);

            for (City neighbor : graph.getNeighbors(current.name)) {
                if (!visited.contains(neighbor.name)) {
                    openList.add(new City(neighbor.name, neighbor.distance));
                }
            }
        }

        // Jika tidak ada jalur yang ditemukan, mengembalikan -1.
        return -1;
    }

    public static void main(String[] args) {
        // Membuat objek graf dan menambahkan koneksi antara kota-kota.
        Graph graph = new Graph();
        graph.addConnection("Jakarta", "Bandung", 150);
        graph.addConnection("Bandung", "Yogyakarta", 300);
        graph.addConnection("Yogyakarta", "Surabaya", 250);
        graph.addConnection("Surabaya", "Malang", 100);
        graph.addConnection("Jakarta", "Surabaya", 400);

        String startCity = "Jakarta";
        String endCity = "Malang";

        // Melakukan pencarian GBFS dari kota awal ke kota tujuan.
        int gbfsResult = gbfs(graph, startCity, endCity);
        if (gbfsResult != -1) {
            System.out.println("GBFS Result: " + gbfsResult + " km");
        } else {
            System.out.println("No path found using GBFS.");
        }
    }
}