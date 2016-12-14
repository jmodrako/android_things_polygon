package pl.modrakowski.raspthings.polygon;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManagerService;

import android.os.Handler;
import android.util.Log;

import java.io.IOException;

import pl.modrakowski.raspthings.MainActivity;

public class BlinkLedCase implements Case {

	private static final String GPIO_PIN_OUT_6 = "BCM6";
	private static final int INTERVAL_BETWEEN_BLINKS_MS = 1000;
	private Handler handler = new Handler();

	private Gpio ledGpio;

	private Runnable blinkRunnable = new Runnable() {
		@Override
		public void run() {
			// Exit if the GPIO is already closed
			if (ledGpio == null) {
				return;
			}

			try {
				// Step 3. Toggle the LED state
				ledGpio.setValue(!ledGpio.getValue());

				// Step 4. Schedule another event after delay.
				handler.postDelayed(blinkRunnable, INTERVAL_BETWEEN_BLINKS_MS);
			} catch (IOException e) {
				Log.e(MainActivity.class.getCanonicalName(), "Error on PeripheralIO API", e);
			}
		}
	};

	@Override public void startCase() {
		try {
			final PeripheralManagerService service = new PeripheralManagerService();

			ledGpio = service.openGpio(GPIO_PIN_OUT_6);
			// Step 2. Configure as an output.
			ledGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);

			// Step 4. Repeat using a handler.
			handler.postDelayed(blinkRunnable, 5000);
		} catch (IOException e) {
			Log.e(MainActivity.class.getCanonicalName(), "Error on PeripheralIO API", e);
		}
	}

	@Override public void stopCase() {
// Step 4. Remove handler events on close.
		handler.removeCallbacks(blinkRunnable);

		// Step 5. Close the resource.
		if (ledGpio != null) {
			try {
				ledGpio.close();
			} catch (IOException e) {
				Log.e(MainActivity.class.getCanonicalName(), "Error on PeripheralIO API", e);
			}
		}
	}
}
