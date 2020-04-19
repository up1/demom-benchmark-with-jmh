package demo;

import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode(Mode.AverageTime)
@OperationsPerInvocation(Hello.MAX_SIZE)
public class Hello {

    public static final int MAX_SIZE = 10000;
    static List<Integer> inputList = new ArrayList<>();
    static {
        for (int i = 0; i < MAX_SIZE; i++) {
            inputList.add(i);
        }
    }

//    public static void main(String[] args) throws RunnerException {
//        Options opt = new OptionsBuilder()
//                .include(demo.Hello.class.getSimpleName())
//                .forks(1)
//                .build();
//
//        new Runner(opt).run();
//    }

    @Benchmark
    public List<Double> forLoop() {
        List<Double> result = new ArrayList<>(inputList.size() / 2 + 1);
        for (Integer i : inputList) {
            if (i % 2 == 0){
                result.add(Math.sqrt(i));
            }
        }
        return result;
    }

    @Benchmark
    public List<Double> useStream() {
        return inputList.stream()
                .filter(i -> i % 2 == 0)
                .map(Math::sqrt)
                .collect(Collectors.toCollection(
                        () -> new ArrayList<>(inputList.size() / 2 + 1)));
    }
    @Benchmark
    public List<Double> useParallelStream() {
        return inputList.parallelStream()
                .filter(i -> i % 2 == 0)
                .map(Math::sqrt)
                .collect(Collectors.toCollection(
                        () -> new ArrayList<>(inputList.size() / 2 + 1)));
    }

}
