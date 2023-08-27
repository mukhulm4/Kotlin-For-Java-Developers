package taxipark

/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> =
    allDrivers.filter { driver ->
        driver !in trips.map { trip -> trip.driver }
    }.toSet()

/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> =
    allPassengers.filter { passenger ->
        trips.filter { trip -> passenger in trip.passengers }.count() >= minTrips
    }.toSet()

/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> =
    allPassengers.filter { passenger ->
        trips.filter { trip ->
            passenger in trip.passengers && trip.driver == driver
        }.count() > 1
    }.toSet()

/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> =
    allPassengers.filter { passenger ->
        trips.filter { trip ->
            passenger in trip.passengers && trip.discount != null
        }.count() > trips.filter { trip ->
            passenger in trip.passengers && trip.discount == null
        }.count()
    }.toSet()

/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? {
    if (trips.isEmpty()) {
        return null
    } else {
        var maxTrips = 0
        var mostFrequentRange: IntRange? = null

        val maxTripDuration = trips.map { trip -> trip.duration }.max() ?: 0
        val mapByNumTrips = mutableMapOf<Int, IntRange>()

        for (i in 0..maxTripDuration step 10) {
            val range = i until i + 10
            val numTripsInRange = trips.count { trip -> trip.duration in range }

            if (numTripsInRange > maxTrips) {
                maxTrips = numTripsInRange
                mostFrequentRange = range
            }

            mapByNumTrips[numTripsInRange] = range
        }

        return mostFrequentRange
    }
}

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {
    if (trips.isEmpty()) {
        return false
    } else {
        val totalCost = trips.map { trip -> trip.cost }.sum()
        val driverCostsSorted = trips
            .groupBy { trip -> trip.driver }
            .mapValues { (_, trips) -> trips.sumByDouble { trip -> trip.cost } }
            .toList()
            .sortedByDescending { (_, value) -> value }
            .toMap()

        var currentSum = 0.0
        var numDrivers = 0
        for (value in driverCostsSorted.values) {
            numDrivers += 1
            currentSum += value
            if (currentSum >= (totalCost * 0.8)) break
        }

        return numDrivers <= (allDrivers.size * 0.2)
    }
}