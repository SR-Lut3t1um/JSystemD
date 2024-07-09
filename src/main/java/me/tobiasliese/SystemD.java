package me.tobiasliese;



import java.lang.foreign.*;

import static me.tobiasliese.Util.readCString;
import static org.systemd.SdBus.*;


public class SystemD implements AutoCloseable {

	private final Arena arena;
	private MemorySegment bus;
	private final MemorySegment dest;
	private final MemorySegment path;

	SystemD() {
		arena = Arena.ofConfined();
		bus = arena.allocate(C_POINTER);
		dest = arena.allocateFrom("org.freedesktop.systemd1");
		path = arena.allocateFrom("/org/freedesktop/systemd1");


		var status = sd_bus_open_system(bus);
		if (status < 0) {
			throw new RuntimeException("System bus error");
		}
	}

	String[] getUnits() {
		var intface = arena.allocateFrom("org.freedesktop.systemd1.Manager");
		var member = arena.allocateFrom("GetUnits");
		SD_BUS.
		var ret_error = arena.allocate(MemoryLayout.structLayout(
				C_POINTER,
				C_POINTER
		));

		var reply = arena.allocate(C_POINTER);
		var type = arena.allocateFrom("s");


		sd_bus_call_method method = sd_bus_call_method.makeInvoker();

		var status = method.apply(bus, dest, path, intface, member, ret_error, reply, type);
		System.out.println(readCString(reply));
		if (status < 0) {
			throw new RuntimeException(
					String.format("%s: %s",
							readCString(ret_error.getAtIndex(C_POINTER, 0)),
							readCString(ret_error.getAtIndex(C_POINTER, 1))
					)
			);
		} else {
			System.out.println("hello world");
		}

		System.out.println(reply.get(ValueLayout.ADDRESS, 0));
		return null;
	}

	@Override
	public void close() throws Exception {
		arena.close();
	}

}