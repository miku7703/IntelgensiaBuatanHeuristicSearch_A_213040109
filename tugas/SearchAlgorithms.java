/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package tugas;

/**
 *
 * @author Kaho
 */

import java.util.*;

class Graph {
    private Map<String, List<City>> graph;

    public Graph() {
        // Inisialisasi graf sebagai HashMap dengan kunci berupa nama kota dan nilai berupa daftar kota tetangga.
        graph = new HashMap<>();
    }

    public void addConnection(String city1, String city2, int distance) {
        // Menambahkan koneksi antara dua kota ke dalam graf.
        // Juga memastikan bahwa kedua kota tersebut ditambahkan sebagai tetangga satu sama lain.
        graph.computeIfAbsent(city1, k -> new ArrayList<>()).add(new City(city2, distance));
        graph.computeIfAbsent(city2, k -> new ArrayList<>()).add(new City(city1, distance));
    }

    public List<City> getNeighbors(String city) {
        // Mengambil daftar kota tetangga dari suatu kota.
        // Mengembalikan daftar kota tetangga dari kota tersebut atau daftar kosong jika kota tidak ditemukan dalam graf.
        return graph.getOrDefault(city, new ArrayList<>());
    }
}

class City {
    String name;
    int distance;

    public City(String name, int distance) {
        // Konstruktor untuk membuat objek kota dengan nama dan jarak ke kota tetangga.
        this.name = name;
        this.distance = distance;
    }
}

public class SearchAlgorithms {
    public static int aStarSearch(Graph graph, String start, String end) {
        // Inisialisasi openList sebagai PriorityQueue dengan perbandingan berdasarkan jarak (distance).
        PriorityQueue<City> openList = new PriorityQueue<>(Comparator.comparingInt(city -> city.distance));
        Map<String, Integer> gScores = new HashMap<>();
        // Menambahkan kota awal ke openList dengan g-score 0.
        openList.add(new City(start, 0));
        gScores.put(start, 0);

        while (!openList.isEmpty()) {
            City current = openList.poll();

            // Jika kota saat ini adalah tujuan, mengembalikan g-score dari tujuan.
            if (current.name.equals(end)) {
                return gScores.get(end);
            }

            for (City neighbor : graph.getNeighbors(current.name)) {
                int tentativeGScore = gScores.get(current.name) + neighbor.distance;

                // Memeriksa apakah tetangga belum memiliki g-score atau memiliki g-score yang lebih besar.
                if (!gScores.containsKey(neighbor.name) || tentativeGScore < gScores.get(neighbor.name)) {
                    gScores.put(neighbor.name, tentativeGScore);
                    // Menghitung f-score dengan menambahkan g-score dan estimasi sisa biaya (heuristik).
                    int fScore = tentativeGScore + Math.abs(neighbor.name.hashCode() - end.hashCode());
                    // Menambahkan tetangga ke openList dengan f-score yang baru.
                    openList.add(new City(neighbor.name, fScore));
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

        // Melakukan pencarian A* Search dari kota awal ke kota tujuan.
        int aStarResult = aStarSearch(graph, startCity, endCity);
        if (aStarResult != -1) {
            System.out.println("A* Search Result: " + aStarResult + " km");
        } else {
            System.out.println("No path found using A* Search.");
        }
    }
}


